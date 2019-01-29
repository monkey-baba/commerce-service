package com.mbb.order.biz.service.impl;

import com.mbb.order.biz.dao.InvoiceMapper;
import com.mbb.order.biz.dao.OrderEntryMapper;
import com.mbb.order.biz.dao.OrderMapper;
import com.mbb.order.biz.dao.PaymentMapper;
import com.mbb.order.biz.dao.SellerRemarkMapper;
import com.mbb.order.biz.model.InvoiceModel;
import com.mbb.order.biz.model.OrderEntryModel;
import com.mbb.order.biz.model.OrderModel;
import com.mbb.order.biz.model.PaymentModel;
import com.mbb.order.biz.model.SellerRemarkModel;
import com.mbb.order.biz.service.OrderService;
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
    private OrderMapper orderMapper;

    @Autowired
    private OrderEntryMapper orderEntryMapper;
    @Autowired
    private InvoiceMapper invoiceMapper;
    @Autowired
    private PaymentMapper paymentMapper;
    @Autowired
    private SellerRemarkMapper sellerRemarkMapper;

    @Override
    public List<OrderModel> getOrders(OrderModel orderModel, Map<String, Object> queryMap) {
        Example example = mapQueryInfo(orderModel, queryMap);
        List<OrderModel> orderModels = orderMapper.selectByExample(example);
        log.info("order size====" + orderModels.size());
        return orderModels;
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
        if (!CollectionUtils.isEmpty(entries)){
            orderEntryMapper.insertList(entries);
        }
        if (!CollectionUtils.isEmpty(payments)){
            paymentMapper.insertList(payments);
        }
        invoiceMapper.insert(invoice);
        if (sellerRemark!=null){
            sellerRemarkMapper.insert(sellerRemark);
        }

    }

    @Override
    public OrderModel getOrderById(Long id) {
        return orderMapper.selectByPrimaryKey(id);
    }

    private Example mapQueryInfo(OrderModel orderModel, Map<String, Object> queryMap) {
        //平台订单号
        String ecsOrderId = orderModel.getEcsOrderId();
        //订单编号
        String code = orderModel.getCode();
        //配货单号
        // TODO: 2019/1/23
        //网店
        Long storeId = orderModel.getStoreId();
        //客户编号
        Long customerId = orderModel.getCustomerId();
        //收货人
        String receiver = orderModel.getReceiver();
        //收货人手机号
        String receiverPhone = orderModel.getReceiverPhone();
        //门店
        Long wareId = orderModel.getPosId();

        Example example = new Example(OrderModel.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(ecsOrderId)) {
            criteria.andLike("ecsOrderId", ecsOrderId + "%");
        }
        if (StringUtils.isNotBlank(code)) {
            criteria.andLike("code", code + "%");
        }
        if (storeId != null) {
            criteria.andEqualTo("storeId", storeId);
        }
        if (customerId != null) {
            criteria.andEqualTo("customerId", customerId);
        }
        if (StringUtils.isNotBlank(receiver)) {
            criteria.andLike("receiver", receiver + "%");
        }
        if (StringUtils.isNotBlank(receiverPhone)) {
            criteria.andLike("receiverPhone", receiverPhone + "%");
        }
        if (wareId != null) {
            criteria.andEqualTo("posId", wareId);
        }
        //订单状态
        List<Long> statusId = (List<Long>) queryMap.get("statusId");
        if (!CollectionUtils.isEmpty(statusId)) {
            criteria.andIn("statusId", statusId);
        }
        //订单类型
        List<Long> orderTypeId = (List<Long>) queryMap.get("orderTypeId");
        if (!CollectionUtils.isEmpty(orderTypeId)) {
            criteria.andIn("orderTypeId", orderTypeId);
        }
        //下单日期
        Object startDateObj = queryMap.get("startDate");
        if (startDateObj != null) {
            Date startDate = (Date) startDateObj;
            criteria.andGreaterThanOrEqualTo("date", startDate);
        }
        Object endDateObj = queryMap.get("endDate");
        if (endDateObj != null) {
            Date endDate = (Date) endDateObj;
            criteria.andLessThanOrEqualTo("date", endDate);
        }
        //支付日期
        Object paymentStartDateObj = queryMap.get("paymentStartDate");
        if (paymentStartDateObj != null) {
            Date paymentStartDate = (Date) paymentStartDateObj;
            criteria.andGreaterThanOrEqualTo("paymentDate", paymentStartDate);
        }
        Object paymentEndDateObj = queryMap.get("paymentEndDate");
        if (paymentEndDateObj != null) {
            Date paymentEndDate = (Date) paymentEndDateObj;
            criteria.andLessThanOrEqualTo("paymentDate", paymentEndDate);
        }
        //订单金额
        Object totalPriceMinObj = queryMap.get("totalPriceMin");
        Object totalPriceMaxObj = queryMap.get("totalPriceMax");
        if (totalPriceMinObj != null) {
            Double totalPriceMin = (Double) totalPriceMinObj;
            criteria.andGreaterThanOrEqualTo("totalPrice", totalPriceMin);
        }
        if (totalPriceMaxObj != null) {
            Double totalPriceMax = (Double) totalPriceMaxObj;
            criteria.andLessThanOrEqualTo("totalPrice", totalPriceMax);
        }
        return example;
    }
}
