package com.mbb.order.biz.service.impl;

import com.mbb.order.biz.dao.OrderEntryMapper;
import com.mbb.order.biz.model.ConsignmentModel;
import com.mbb.order.biz.model.OrderEntryModel;
import com.mbb.order.biz.service.OrderEntryService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Builder;
import tk.mybatis.mapper.util.Sqls;

@Service
@Slf4j
public class OrderEntryServiceImpl implements OrderEntryService {

    @Autowired
    private OrderEntryMapper orderEntryMapper;

    @Override
    public void insertEntries(List<OrderEntryModel> entries) {
        orderEntryMapper.insertList(entries);
    }

    @Override
    public List<OrderEntryModel> getEntriesByOrderId(Long id) {
        Builder builder = Example.builder(OrderEntryModel.class);
        builder.where(Sqls.custom().andEqualTo("orderId",id));
        return orderEntryMapper.selectByExample(builder.build());
    }
}
