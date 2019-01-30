package com.mbb.order.rest.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lxm.idgenerator.service.intf.IdService;
import com.mbb.basic.common.dto.AddressData;
import com.mbb.basic.common.dto.DictValueData;
import com.mbb.customer.common.dto.CustomerData;
import com.mbb.order.adapter.AddressAdapter;
import com.mbb.order.adapter.CustomerAdapter;
import com.mbb.order.adapter.DictAdapter;
import com.mbb.order.adapter.ProductAdapter;
import com.mbb.order.adapter.PosAdapter;
import com.mbb.order.biz.model.InvoiceModel;
import com.mbb.order.biz.model.OrderEntryModel;
import com.mbb.order.biz.model.OrderModel;
import com.mbb.order.biz.model.PaymentModel;
import com.mbb.order.biz.model.SellerRemarkModel;
import com.mbb.order.biz.service.OrderService;
import com.mbb.order.rest.dto.CustomerQuery;
import com.mbb.order.rest.dto.OrderCreateData;
import com.mbb.order.rest.dto.OrderDetailData;
import com.mbb.order.rest.dto.OrderInfoResp;
import com.mbb.order.rest.dto.OrderListQuery;
import com.mbb.order.rest.dto.SkuQuery;
import com.mbb.order.rest.dto.StoreQuery;
import com.mbb.product.common.dto.SkuData;
import com.mbb.stock.common.dto.PosDetailData;
import com.mbb.stock.common.dto.StoreInfoDto;

import java.util.*;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * ${DESCRIPTION}
 *
 * @author lf
 * @create 2019-01-17 18:02
 */
@RestController
@RequestMapping("/api/v1/order")
@Slf4j
public class OrderController extends BaseController {

    @Autowired
    private AddressAdapter addressAdapter;
    @Autowired
    private CustomerAdapter customerAdapter;
    @Autowired
    private DictAdapter dictAdapter;
    @Autowired
    private IdService idService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductAdapter productAdapter;
    @Autowired
    private PosAdapter posAdapter;

    @GetMapping("/info")
    public ResponseEntity getOrders(OrderListQuery orderListQuery) {
        Map<String, Object> parameters = buildParameters(orderListQuery);
        //开启分页
        PageHelper.startPage(orderListQuery.getPageNum(), orderListQuery.getPageSize());
        //查询数据
        List<OrderModel> orders = orderService.getOrders(parameters);
        //获取页码等信息
        PageInfo<OrderModel> origin = PageInfo.of(orders);
        //从model转data
        List<OrderInfoResp> orderInfoRespList = origin.getList().stream().map(order -> {
            OrderInfoResp orderInfoResp = new OrderInfoResp();
            convertOrder(order, orderInfoResp);
            return orderInfoResp;
        }).collect(Collectors.toList());
        //用data生成新的分页数据
        PageInfo<OrderInfoResp> result = PageInfo.of(orderInfoRespList);
        result.setTotal(origin.getTotal());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/create")
    public ResponseEntity createOrder(@RequestBody OrderCreateData data) {
        // 创建订单
        OrderModel order = new OrderModel();
        order.setId(idService.genId());
        order.setVersion(1);
        order.setOrderTypeId(data.getOrderType());
        order.setCode(data.getCode());
        order.setPlatformId(data.getPlatform());
        order.setStoreId(data.getStore());
        order.setDeliveryTypeId(data.getDeliveryType());
        order.setCarrierId(data.getCarrier());
        order.setCustomerId(data.getCustomer());
        order.setPosId(data.getPos());
        order.setReceiver(data.getReceiver());
        order.setReceiverPhone(data.getReceiverPhone());
        order.setRemark(data.getRemark());
        //订单金额
        order.setTotalPrice(data.getTotalPrice());
        order.setDeliveryCost(data.getDeliveryCost());
        //手工创建
        order.setOrderSourceId(1401L);
        order.setDate(new Date());
        //已保存状态
        order.setStatusId(1101L);
//        order.setChannelId();

        //地址
        AddressData addrData = new AddressData();
        addrData.setAddress(data.getPcd());
        addrData.setName(data.getReceiver());
        addrData.setPhone(data.getReceiverPhone());
        addrData.setDetail(data.getAddress());
        // 保存地址 TODO 分布式事务回滚
        order.setAddressId(addressAdapter.saveAddress(addrData));

        //卖家备注
        String sellerRemark = data.getSellerRemark();
        SellerRemarkModel sellerRemarkModel = null;
        if (StringUtils.isNotEmpty(sellerRemark)) {
            sellerRemarkModel = new SellerRemarkModel();
            sellerRemarkModel.setId(idService.genId());
            sellerRemarkModel.setVersion(1);
            sellerRemarkModel.setRemark(sellerRemark);
            sellerRemarkModel.setDate(new Date());
            sellerRemarkModel.setOrderId(order.getId());
            sellerRemarkModel.setUserId(1L);
        }

        //发票
        InvoiceModel inv = new InvoiceModel();
        inv.setId(idService.genId());
        inv.setVersion(1);
        inv.setApplied(data.getInvoice());
        inv.setOrderId(order.getId());
        if (data.getInvoice() != null && data.getInvoice()) {
            inv.setTypeId(data.getInvoiceType());
            inv.setTitle(data.getInvoiceTitle());
        }
        inv.setAmount(data.getTotalPrice());
        //支付
        List<PaymentModel> paymentModels = null;
        if (CollectionUtils.isNotEmpty(data.getPayments())) {
            paymentModels = data.getPayments().stream().map(p -> {
                PaymentModel payment = new PaymentModel();
                payment.setAmount(p.getAmount());
                payment.setDate(new Date());
                payment.setOrderId(order.getId());
                payment.setId(idService.genId());
                payment.setVersion(1);
                payment.setTypeId(p.getType().getId());
                return payment;
            }).collect(Collectors.toList());
            order.setPaymentDate(new Date());
        }

        //订单行
        final Double[] subTotal = {0D};
        Double[] discount = {0D};
        final Integer[] lineNum = {0};
        List<OrderEntryModel> entries = data.getEntries().stream().map(e -> {
            OrderEntryModel entry = new OrderEntryModel();
            entry.setId(idService.genId());
            entry.setVersion(1);
            entry.setOrderId(order.getId());
            entry.setBasePrice(e.getBasePrice());
            entry.setDiscount(e.getDiscount());
            entry.setQuantity(e.getQuantity());
            entry.setShippedQuantity(e.getShippedQuantity());
            entry.setSkuId(e.getId());
            entry.setTotalPrice(e.getTotalPrice());
            entry.setEntryNum(++lineNum[0]);
            subTotal[0] += (e.getBasePrice() * e.getQuantity());
            discount[0] += (e.getDiscount() * e.getQuantity());
            return entry;
        }).collect(Collectors.toList());
        order.setSubTotal(subTotal[0]);
        order.setDiscount(discount[0]);

        orderService.createOrder(order,entries,paymentModels,inv,sellerRemarkModel);
        return ResponseEntity.ok("1");
    }


    @GetMapping("/customer/list")
    public ResponseEntity getCustomers(CustomerQuery customerQuery) {
        PageInfo<CustomerData> customerList = customerAdapter
                .getCustomers(customerQuery.getCode(), customerQuery.getName(),
                        customerQuery.getPageNum(), customerQuery.getPageSize());
        return ResponseEntity.ok(customerList);
    }

    @GetMapping("/pos/list")
    public ResponseEntity getPosList(StoreQuery storeQuery) {
        PageInfo<StoreInfoDto> customerList = posAdapter
                .getStores(storeQuery.getCode(), storeQuery.getName(), storeQuery.getPageNum(),
                        storeQuery.getPageSize());
        return ResponseEntity.ok(customerList);
    }

    @GetMapping("/sku/list")
    public ResponseEntity getPosList(SkuQuery skuQuery) {
        PageInfo<SkuData> customerList = productAdapter
                .getSkus(skuQuery.getCode(), skuQuery.getName(),
                        skuQuery.getPageNum(), skuQuery.getPageSize());
        return ResponseEntity.ok(customerList);
    }

    private void convertOrder(OrderModel orderModel, OrderInfoResp orderInfoResp) {
        //id
        orderInfoResp.setId(orderModel.getId());
        //平台订单号
        orderInfoResp.setEcsOrderId(orderModel.getEcsOrderId());
        //店铺
        Long storeId = orderModel.getStoreId();
        if (storeId != null) {
            DictValueData baseStoreData = dictAdapter.getDictValue(storeId);
            orderInfoResp.setStoreId(storeId);
            orderInfoResp.setStoreName(baseStoreData.getName());
        }
        //订单编号
        orderInfoResp.setCode(orderModel.getCode());
        //门店
        Long posId = orderModel.getPosId();
        if (posId != null) {
            orderInfoResp.setPosName(posAdapter.getStoreNameById(posId));
        }
        //订单类型
        Long orderTypeId = orderModel.getOrderTypeId();
        if (orderTypeId != null) {
            DictValueData orderTypeData = dictAdapter.getDictValue(orderTypeId);
            orderInfoResp.setOrderTypeId(orderTypeId);
            orderInfoResp.setOrderTypeName(orderTypeData.getName());
        }
        //订单状态
        Long statusId = orderModel.getStatusId();
        if (statusId != null) {
            DictValueData statusData = dictAdapter.getDictValue(statusId);
            orderInfoResp.setStatusId(statusId);
            orderInfoResp.setStatusName(statusData.getName());
        }
        //订单金额
        orderInfoResp.setTotalPrice(orderModel.getTotalPrice());
        //收件人
        orderInfoResp.setReceiver(orderModel.getReceiver());
        //收件人手机号
        orderInfoResp.setReceiverPhone(orderModel.getReceiverPhone());
        //收件地址
        Long addressId = orderModel.getAddressId();
        if (addressId != null) {
            AddressData addressData = addressAdapter.getAddress(addressId);
            orderInfoResp.setAddressId(addressId);
            orderInfoResp.setAddressName(addressData.getName());
        }
        //下单时间
        orderInfoResp.setDate(orderModel.getDate());
        //支付时间
        orderInfoResp.setPaymentDate(orderModel.getPaymentDate());
    }


    @GetMapping("/detail")
    public ResponseEntity detail(@RequestParam Long id){
        OrderModel order = orderService.getOrderDetailById(id);
        OrderDetailData data = new OrderDetailData();
        BeanCopier.create(OrderModel.class, OrderDetailData.class, false)
                .copy(order, data, null);
        data.setStatus(dictAdapter.getDictValueName(order.getStatusId()));
        data.setOrderType(dictAdapter.getDictValueName(order.getOrderTypeId()));
        data.setDeliveryType(dictAdapter.getDictValueName(order.getDeliveryTypeId()));
        data.setCustomer(customerAdapter.getCustomerName(order.getCustomerId()));
        data.setPlatform(dictAdapter.getDictValueName(order.getPlatformId()));
        data.setStore(dictAdapter.getDictValueName(order.getStoreId()));
        data.setPos(posAdapter.getStoreNameById(order.getPosId()));
        data.setOrderSource(dictAdapter.getDictValueName(order.getOrderSourceId()));
        data.setChannel(dictAdapter.getDictValueName(order.getChannelId()));

        data.setAddress(addressAdapter.getAddress(order.getAddressId()));
        data.setPointPos(posAdapter.getPosDetail(order.getPointPosId()));

        return ResponseEntity.ok(data);
    }


    private Map<String, Object> buildParameters(OrderListQuery orderListQuery) {
        if (orderListQuery == null) {
            return Collections.emptyMap();
        }
        Map<String, Object> parameters = new HashMap<>(18);
        parameters.put("ecsOrderId", orderListQuery.getEcsOrderId());
        parameters.put("code", orderListQuery.getCode());
        parameters.put("consignmentCode", orderListQuery.getConsignmentCode());
        parameters.put("storeId", orderListQuery.getStoreId());
        parameters.put("customerId", orderListQuery.getCustomerId());
        parameters.put("receiver", orderListQuery.getReceiver());
        parameters.put("receiverPhone", orderListQuery.getReceiverPhone());
        parameters.put("posId", orderListQuery.getPosId());
        parameters.put("totalPriceMin", orderListQuery.getTotalPriceMin());
        parameters.put("totalPriceMax", orderListQuery.getTotalPriceMax());
        parameters.put("startDate", orderListQuery.getStartDate());
        parameters.put("endDate", orderListQuery.getEndDate());
        parameters.put("paymentStartDate", orderListQuery.getPaymentStartDate());
        parameters.put("paymentEndDate", orderListQuery.getPaymentEndDate());
        parameters.put("statusId", orderListQuery.getStatusId());
        parameters.put("orderTypeId", orderListQuery.getOrderTypeId());
        return parameters;
    }

}
