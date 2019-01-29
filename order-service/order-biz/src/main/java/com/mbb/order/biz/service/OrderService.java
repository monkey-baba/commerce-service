package com.mbb.order.biz.service;

import com.mbb.order.biz.model.InvoiceModel;
import com.mbb.order.biz.model.OrderEntryModel;
import com.mbb.order.biz.model.OrderModel;

import com.mbb.order.biz.model.PaymentModel;
import com.mbb.order.biz.model.SellerRemarkModel;
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

    void createOrder(OrderModel orderModel,
            List<OrderEntryModel> entries,
            List<PaymentModel> payments,
            InvoiceModel invoice,
            SellerRemarkModel sellerRemark);

    OrderModel getOrderById(Long id);

    OrderModel getOrderDetailById(Long id);
}
