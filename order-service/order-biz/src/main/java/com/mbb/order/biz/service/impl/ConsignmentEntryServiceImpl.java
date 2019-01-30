package com.mbb.order.biz.service.impl;

import com.mbb.order.biz.dao.ConsignmentEntryMapper;
import com.mbb.order.biz.model.ConsignmentEntryModel;
import com.mbb.order.biz.service.ConsignmentEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

import java.util.List;

/**
 * @author hyx
 * @title ConsignmentEntryServiceImpl
 * @description
 * @date 2019/1/30
 */
@Service
public class ConsignmentEntryServiceImpl implements ConsignmentEntryService {

    @Autowired
    private ConsignmentEntryMapper consignmentEntryMapper;

    @Override
    public List<ConsignmentEntryModel> getConsignmentEntriesByConsignmentId(Long id) {
        Example.Builder builder = Example.builder(ConsignmentEntryModel.class);
        builder.where(Sqls.custom().andEqualTo("consignmentId", id));
        return consignmentEntryMapper.selectByExample(builder.build());
    }
}
