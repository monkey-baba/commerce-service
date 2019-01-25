package com.mbb.order.biz.service;

import com.mbb.order.biz.model.OrderModel;

import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author lf
 * @create 2019-01-17 18:04
 */
public interface OrderService {
    /**
     * 订单查询
     *
     * @param orderModel
     * @return
     */
    List<OrderModel> getOrders(OrderModel orderModel, Map<String, Object> queryMap);

    void createOrder(OrderModel orderModel);

}
