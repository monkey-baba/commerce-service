package com.mbb.order.rest.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lxm.idgenerator.service.intf.IdService;
import com.mbb.basic.common.dto.DictValueData;
import com.mbb.order.biz.model.OrderModel;
import com.mbb.order.biz.service.OrderService;
import com.mbb.order.rest.dto.OrderCreateData;
import com.mbb.order.rest.dto.OrderInfoResp;
import com.mbb.order.rest.dto.OrderListQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ${DESCRIPTION}
 *
 * @author lf
 * @create 2019-01-17 18:02
 */
@RestController
@RequestMapping("api/v1/order")
public class OrderController extends BaseController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private IdService idService;

    @GetMapping("/info")
    public ResponseEntity getStocks(OrderListQuery orderListQuery) {
        OrderModel orderModel = new OrderModel();
        orderModel.setEcsOrderId(orderListQuery.getEcsOrderId());
        orderModel.setCode(orderListQuery.getCode());
        orderModel.setStoreId(orderListQuery.getStoreId());
        orderModel.setCustomerId(orderListQuery.getCustomerId());
        orderModel.setReceiver(orderListQuery.getReceiver());
        orderModel.setReceiverPhone(orderListQuery.getReceiverPhone());
        orderModel.setWareId(orderListQuery.getWareId());
        orderModel.setStatusId(orderListQuery.getStatusId());
        orderModel.setOrderTypeId(orderListQuery.getOrderTypeId());

        //开启分页
        PageHelper.startPage(orderListQuery.getPageNum(), orderListQuery.getPageSize());
        //查询数据
        List<OrderModel> orders = orderService.getOrders(orderModel);
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
        orderModel.setWareId(orderCreateData.getWareId());
        orderModel.setTotalPrice(orderCreateData.getTotalPrice());
        orderModel.setStatusId(orderCreateData.getStatusId());
        orderModel.setOrderTypeId(orderCreateData.getOrderTypeId());
        orderService.createOrder(orderModel);
        OrderInfoResp orderInfoResp = new OrderInfoResp();
        convertOrder(orderModel, orderInfoResp);
        return ResponseEntity.ok(orderInfoResp);
    }

    @GetMapping("/enums")
    public ResponseEntity getEnums(@RequestParam String type) {
        List<DictValueData> dictValues = orderService.getEnums(type);
        return ResponseEntity.ok(dictValues);
    }

    private void convertOrder(OrderModel orderModel, OrderInfoResp orderInfoResp) {
        orderInfoResp.setId(orderModel.getId());
        orderInfoResp.setEcsOrderId(orderModel.getEcsOrderId());
        orderInfoResp.setStoreId(orderModel.getStoreId());
        orderInfoResp.setCode(orderModel.getCode());
        orderInfoResp.setWareId(orderModel.getWareId());
        orderInfoResp.setOrderTypeId(orderModel.getOrderTypeId());
        orderInfoResp.setStatusId(orderModel.getStatusId());
        orderInfoResp.setTotalPrice(orderModel.getTotalPrice());
        orderInfoResp.setReceiver(orderModel.getReceiver());
        orderInfoResp.setReceiverPhone(orderModel.getReceiverPhone());
        orderInfoResp.setAddressId(orderModel.getAddressId());
        orderInfoResp.setDate(orderModel.getDate());
        orderInfoResp.setPaymentDate(orderModel.getPaymentDate());
    }
}
