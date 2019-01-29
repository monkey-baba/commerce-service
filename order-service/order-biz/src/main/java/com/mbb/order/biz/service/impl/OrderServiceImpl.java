package com.mbb.order.biz.service.impl;

import com.mbb.order.biz.dao.OrderMapper;
import com.mbb.order.biz.model.ConsignmentModel;
import com.mbb.order.biz.model.InvoiceModel;
import com.mbb.order.biz.model.OrderEntryModel;
import com.mbb.order.biz.model.OrderModel;
import com.mbb.order.biz.model.PaymentModel;
import com.mbb.order.biz.model.SellerRemarkModel;
import com.mbb.order.biz.service.ConsignmentService;
import com.mbb.order.biz.service.InvoiceService;
import com.mbb.order.biz.service.OrderEntryService;
import com.mbb.order.biz.service.OrderService;
import com.mbb.order.biz.service.PaymentService;
import com.mbb.order.biz.service.RemarkService;
import java.util.Date;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

/**
 * ${DESCRIPTION}
 *
 * @author lf
 * @create 2019-01-17 18:05
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private OrderEntryService orderEntryService;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private RemarkService remarkService;

    @Autowired
    private ConsignmentService consignmentService;

    @Override
    public List<OrderModel> getOrders(Map<String, Object> parameters) {
        return orderMapper.getOrders(parameters);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createOrder(OrderModel orderModel,
            List<OrderEntryModel> entries,
            List<PaymentModel> payments,
            InvoiceModel invoice,
            SellerRemarkModel sellerRemark) {
        //保存订单
        orderMapper.insert(orderModel);
        //保存订单行
        if (!CollectionUtils.isEmpty(entries)) {
            orderEntryService.insertEntries(entries);
        }
        if (!CollectionUtils.isEmpty(payments)) {
            paymentService.insertPayments(payments);
        }
        invoiceService.insert(invoice);
        if (sellerRemark != null) {
            remarkService.insert(sellerRemark);
        }

    }

    @Override
    public OrderModel getOrderDetailById(Long id) {
        OrderModel order = this.getOrderById(id);

        List<OrderEntryModel> entries = orderEntryService.getEntriesByOrderId(order.getId());
        order.setEntries(entries);

        List<ConsignmentModel> consignments = consignmentService.getConsignmentsByOrderId(order.getId());
        order.setConsignments(consignments);

        List<SellerRemarkModel> sellerRemarks = remarkService.getRemarksByOrderId(order.getId());
        order.setSellerRemarks(sellerRemarks);

        List<PaymentModel> payments = paymentService.getPaymentsByOrderId(order.getId());
        order.setPayments(payments);

        InvoiceModel invoice = invoiceService.getInvoiceByOrderId(order.getId());
        order.setInvoice(invoice);
        return order;
    }

    @Override
    public OrderModel getOrderById(Long id) {
        return orderMapper.selectByPrimaryKey(id);
    }

}
