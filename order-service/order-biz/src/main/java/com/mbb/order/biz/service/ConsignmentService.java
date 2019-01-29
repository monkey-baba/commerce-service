package com.mbb.order.biz.service;

import com.mbb.order.biz.model.ConsignmentModel;

import java.util.List;
import java.util.Map;

/**
 * @author hyx
 * @title ConsignmentService
 * @description
 * @date 2019/1/28
 */
public interface ConsignmentService {

    /**
     * 查询配货单
     *
     * @param parameters
     * @return
     */
    List<ConsignmentModel> getConsignments(Map<String, Object> parameters);

    List<ConsignmentModel> getConsignmentsByOrderId(Long id);
}
