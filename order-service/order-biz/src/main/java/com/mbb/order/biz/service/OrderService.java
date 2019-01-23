package com.mbb.order.biz.service;

import com.mbb.basic.common.dto.DictValueData;
import com.mbb.order.biz.model.OrderModel;

import java.util.List;

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
    List<OrderModel> getOrders(OrderModel orderModel);

    void createOrder(OrderModel orderModel);

}
