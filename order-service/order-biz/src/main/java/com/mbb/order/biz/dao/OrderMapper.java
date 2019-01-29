package com.mbb.order.biz.dao;

import com.mbb.order.biz.model.OrderModel;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

/**
 * ORM Dao 包路径
 */
public interface OrderMapper extends Mapper<OrderModel> {

}