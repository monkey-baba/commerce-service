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
import com.mbb.order.adapter.StoreAdapter;
import com.mbb.order.biz.model.OrderModel;
import com.mbb.order.biz.service.OrderService;
import com.mbb.order.rest.dto.CustomerQuery;
import com.mbb.order.rest.dto.OrderCreateData;
import com.mbb.order.rest.dto.OrderInfoResp;
import com.mbb.order.rest.dto.OrderListQuery;
import com.mbb.order.rest.dto.SkuQuery;
import com.mbb.order.rest.dto.StoreQuery;
import com.mbb.product.common.dto.SkuData;
import com.mbb.stock.common.dto.StoreInfoDto;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ${DESCRIPTION}
 *
 * @author lf
 * @create 2019-01-17 18:02
 */
@RestController
@RequestMapping("/api/v1/order")
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
    private StoreAdapter storeAdapter;

    @Autowired
    private ProductAdapter productAdapter;

    @GetMapping("/info")
    public ResponseEntity getOrders(OrderListQuery orderListQuery) {
        OrderModel orderModel = new OrderModel();
        orderModel.setEcsOrderId(orderListQuery.getEcsOrderId());
        orderModel.setCode(orderListQuery.getCode());
        orderModel.setStoreId(orderListQuery.getStoreId());
        orderModel.setCustomerId(orderListQuery.getCustomerId());
        orderModel.setReceiver(orderListQuery.getReceiver());
        orderModel.setReceiverPhone(orderListQuery.getReceiverPhone());
        orderModel.setPosId(orderListQuery.getPosId());
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("startDate", orderListQuery.getStartDate());
        queryMap.put("endDate", orderListQuery.getEndDate());
        queryMap.put("paymentStartDate", orderListQuery.getPaymentStartDate());
        queryMap.put("paymentEndDate", orderListQuery.getPaymentEndDate());
        queryMap.put("totalPriceMin", orderListQuery.getTotalPriceMin());
        queryMap.put("totalPriceMax", orderListQuery.getTotalPriceMax());
        queryMap.put("statusId", orderListQuery.getStatusId());
        queryMap.put("orderTypeId", orderListQuery.getOrderTypeId());
        //开启分页
        PageHelper.startPage(orderListQuery.getPageNum(), orderListQuery.getPageSize());
        //查询数据
        List<OrderModel> orders = orderService.getOrders(orderModel, queryMap);
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
    public ResponseEntity createOrder(@RequestBody OrderCreateData orderCreateData) {
        OrderModel orderModel = new OrderModel();
        orderModel.setId(idService.genId());
        orderModel.setEcsOrderId(orderCreateData.getEcsOrderId());
        orderModel.setCode(orderCreateData.getCode());
        orderModel.setStoreId(orderCreateData.getStoreId());
        orderModel.setCustomerId(orderCreateData.getCustomerId());
        orderModel.setReceiver(orderCreateData.getReceiver());
        orderModel.setReceiverPhone(orderCreateData.getReceiverPhone());
        orderModel.setPosId(orderCreateData.getWareId());
        orderModel.setTotalPrice(orderCreateData.getTotalPrice());
        orderModel.setStatusId(orderCreateData.getStatusId());
        orderModel.setOrderTypeId(orderCreateData.getOrderTypeId());
        orderService.createOrder(orderModel);
        OrderInfoResp orderInfoResp = new OrderInfoResp();
        convertOrder(orderModel, orderInfoResp);
        return ResponseEntity.ok(orderInfoResp);
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
        PageInfo<StoreInfoDto> customerList = storeAdapter
                .getCustomers(storeQuery.getCode(), storeQuery.getName(), storeQuery.getPageNum(),
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
        // TODO: 2019/1/25
        Long posId = orderModel.getPosId();
        if (posId != null) {
            orderInfoResp.setPosName(storeAdapter.getNameById(posId));
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
}
