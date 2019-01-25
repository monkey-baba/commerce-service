package com.mbb.order.rest.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lxm.idgenerator.service.intf.IdService;
import com.mbb.basic.common.dto.AddressData;
import com.mbb.basic.common.dto.DictValueData;
import com.mbb.order.adapter.AddressAdapter;
import com.mbb.order.adapter.OrderServiceAdapter;
import com.mbb.order.biz.model.OrderModel;
import com.mbb.order.biz.service.OrderService;
import com.mbb.order.rest.dto.OrderCreateData;
import com.mbb.order.rest.dto.OrderInfoResp;
import com.mbb.order.rest.dto.OrderListQuery;
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
    private IdService idService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderServiceAdapter orderServiceAdapter;

    @GetMapping("/info")
    public ResponseEntity getStocks(OrderListQuery orderListQuery) {
        OrderModel orderModel = new OrderModel();
        orderModel.setEcsOrderId(orderListQuery.getEcsOrderId());
        orderModel.setCode(orderListQuery.getCode());
        orderModel.setStoreId(orderListQuery.getStoreId());
        orderModel.setCustomerId(orderListQuery.getCustomerId());
        orderModel.setReceiver(orderListQuery.getReceiver());
        orderModel.setReceiverPhone(orderListQuery.getReceiverPhone());
        orderModel.setPosId(orderListQuery.getPosId());
//        orderModel.setStatusId(orderListQuery.getStatusId());
//        orderModel.setOrderTypeId(orderListQuery.getOrderTypeId());
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("startDate", orderListQuery.getStartDate());
        queryMap.put("endDate", orderListQuery.getEndDate());
        queryMap.put("paymentStartDate", orderListQuery.getPaymentStartDate());
        queryMap.put("paymentEndDate", orderListQuery.getPaymentEndDate());
        queryMap.put("totalPriceMin", orderListQuery.getTotalPriceMin());
        queryMap.put("totalPriceMax", orderListQuery.getTotalPriceMax());
        queryMap.put("statusId", orderListQuery.getNewStatusId());
        queryMap.put("orderTypeId", orderListQuery.getNewOrderTypeId());
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
    public ResponseEntity createStock(@RequestBody OrderCreateData orderCreateData) {
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

    @GetMapping("/orderstatus")
    public ResponseEntity getOrderStatus() {
        List<DictValueData> orderStatusDataList = orderServiceAdapter.getOrderStatus();
        return ResponseEntity.ok(orderStatusDataList);
    }


    @GetMapping("/ordertypes")
    public ResponseEntity getOrderType() {
        List<DictValueData> orderTypeDataList = orderServiceAdapter.getOrderType();
        return ResponseEntity.ok(orderTypeDataList);
    }

    @GetMapping("/stores")
    public ResponseEntity getBaseStores() {
        List<DictValueData> baseStoreDataList = orderServiceAdapter.getBaseStores();
        return ResponseEntity.ok(baseStoreDataList);
    }

    @GetMapping("/platforms")
    public ResponseEntity getPlatforms() {
        List<DictValueData> valueDataList = orderServiceAdapter.getPlatforms();
        return ResponseEntity.ok(valueDataList);
    }

    @GetMapping("/deliveryTypes")
    public ResponseEntity getDeliveryTypes() {
        List<DictValueData> valueDataList = orderServiceAdapter.getDeliveryTypes();
        return ResponseEntity.ok(valueDataList);
    }

    @GetMapping("/carriers")
    public ResponseEntity getCarriers() {
        List<DictValueData> valueDataList = orderServiceAdapter.getCarriers();
        return ResponseEntity.ok(valueDataList);
    }

    @GetMapping("/invoiceTypes")
    public ResponseEntity getInvoiceTypes() {
        List<DictValueData> valueDataList = orderServiceAdapter.getInvoiceTypes();
        return ResponseEntity.ok(valueDataList);
    }

    private void convertOrder(OrderModel orderModel, OrderInfoResp orderInfoResp) {
        //id
        orderInfoResp.setId(orderModel.getId());
        //平台订单号
        orderInfoResp.setEcsOrderId(orderModel.getEcsOrderId());
        //店铺
        Long storeId = orderModel.getStoreId();
        if (storeId != null) {
            DictValueData baseStoreData = orderServiceAdapter.getDictValue(storeId);
            orderInfoResp.setStoreId(storeId);
            orderInfoResp.setStoreName(baseStoreData.getName());
        }
        //订单编号
        orderInfoResp.setCode(orderModel.getCode());
        //门店
        orderInfoResp.setPosId(orderModel.getPosId());
        //订单类型
        Long orderTypeId = orderModel.getOrderTypeId();
        if (orderTypeId != null) {
            DictValueData orderTypeData = orderServiceAdapter.getDictValue(orderTypeId);
            orderInfoResp.setOrderTypeId(orderTypeId);
            orderInfoResp.setOrderTypeName(orderTypeData.getName());
        }
        //订单状态
        Long statusId = orderModel.getStatusId();
        if (statusId != null) {
            DictValueData statusData = orderServiceAdapter.getDictValue(statusId);
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
