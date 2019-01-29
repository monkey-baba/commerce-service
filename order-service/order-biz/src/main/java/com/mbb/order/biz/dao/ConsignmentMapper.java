package com.mbb.order.biz.dao;

import com.mbb.order.biz.model.ConsignmentModel;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author hyx
 * @title ConsignmentMapper
 * @description
 * @date 2019/1/28
 */
public interface ConsignmentMapper extends Mapper<ConsignmentModel> {

    /**
     * 根据指定条件查询配货单
     *
     * @param parameters 条件
     * @return 配货单集合
     */
    List<ConsignmentModel> getConsignments(Map<String, Object> parameters);
}
