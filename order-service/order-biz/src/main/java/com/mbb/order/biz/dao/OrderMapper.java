package com.mbb.order.biz.dao;

import com.mbb.order.biz.model.OrderModel;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * ORM Dao 包路径
 */
public interface OrderMapper extends Mapper<OrderModel> {
    /**
     * 根据指定条件查询订单
     *
     * @param parameters 条件
     * @return 订单集合
     */
    List<OrderModel> getOrders(Map<String, Object> parameters);
}