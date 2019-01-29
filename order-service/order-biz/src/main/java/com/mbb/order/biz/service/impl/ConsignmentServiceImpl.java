package com.mbb.order.biz.service.impl;

import com.mbb.order.biz.dao.ConsignmentMapper;
import com.mbb.order.biz.model.ConsignmentModel;
import com.mbb.order.biz.service.ConsignmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author hyx
 * @title ConsignmentServiceImpl
 * @description
 * @date 2019/1/28
 */
@Service
@Slf4j
public class ConsignmentServiceImpl implements ConsignmentService {

    @Autowired
    private ConsignmentMapper consignmentMapper;

    @Override
    public List<ConsignmentModel> getConsignments(Map<String, Object> parameters) {
        return consignmentMapper.getConsignments(parameters);
    }
}
