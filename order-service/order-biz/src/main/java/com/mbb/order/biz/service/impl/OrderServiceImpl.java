package com.mbb.order.biz.service.impl;

import com.mbb.order.biz.dao.OrderMapper;
import com.mbb.order.biz.model.OrderModel;
import com.mbb.order.biz.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

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

    @Override
    public List<OrderModel> getOrders(OrderModel orderModel) {
        Example example = mapQueryInfo(orderModel);
        List<OrderModel> orderModels = orderMapper.selectByExample(example);
        log.info("order size====" + orderModels.size());
        return orderModels;
    }

    @Override
    public void createOrder(OrderModel orderModel) {
        if (orderModel != null) {
            orderMapper.insert(orderModel);
        }
    }

    private Example mapQueryInfo(OrderModel orderModel) {
        //商品编码
        String ecsOrderId = orderModel.getEcsOrderId();
        //商品编码
        String code = orderModel.getCode();
        //商品编码
        Long storeId = orderModel.getStoreId();
        //商品编码
        Long customerId = orderModel.getCustomerId();
        //商品编码
        String receiver = orderModel.getReceiver();
        //商品编码
        String receiverPhone = orderModel.getReceiverPhone();
        //商品编码
        Long wareId = orderModel.getWareId();
        //商品编码
        Long statusId = orderModel.getStatusId();
        //商品编码
        Long orderTypeId = orderModel.getOrderTypeId();
        //仓库名称
        Example example = new Example(OrderModel.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(ecsOrderId)) {
            criteria.andEqualTo("ecsOrderId", ecsOrderId);
        }
        if (StringUtils.isNotBlank(code)) {
            criteria.andEqualTo("code", code);
        }
        if (storeId != null) {
            criteria.andEqualTo("storeId", storeId);
        }
        if (customerId != null) {
            criteria.andEqualTo("customerId", customerId);
        }
        if (StringUtils.isNotBlank(receiver)) {
            criteria.andEqualTo("receiver", receiver);
        }
        if (StringUtils.isNotBlank(receiverPhone)) {
            criteria.andEqualTo("receiverPhone", receiverPhone);
        }
        if (wareId != null) {
            criteria.andEqualTo("wareId", wareId);
        }
        if (statusId != null) {
            criteria.andEqualTo("statusId", statusId);
        }
        if (orderTypeId != null) {
            criteria.andEqualTo("orderTypeId", orderTypeId);
        }
        return example;
    }
}
