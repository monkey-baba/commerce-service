package com.mbb.order.biz.service.impl;

import com.mbb.order.biz.dao.ConsignmentMapper;
import com.mbb.order.biz.model.ConsignmentModel;
import com.mbb.order.biz.service.ConsignmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Builder;
import tk.mybatis.mapper.util.Sqls;

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

    @Override
    public List<ConsignmentModel> getConsignmentsByOrderId(Long id) {
        Builder builder = Example.builder(ConsignmentModel.class);
        builder.where(Sqls.custom().andEqualTo("orderId", id));
        return consignmentMapper.selectByExample(builder.build());
    }

    @Override
    public ConsignmentModel getConsignmentById(Long id) {
        return consignmentMapper.selectByPrimaryKey(id);
    }
}
