package com.mbb.order.biz.service.impl;

import com.mbb.order.biz.dao.OrderEntryMapper;
import com.mbb.order.biz.model.OrderEntryModel;
import com.mbb.order.biz.service.OrderEntryService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderEntryServiceImpl implements OrderEntryService {

    @Autowired
    private OrderEntryMapper orderEntryMapper;

    @Override
    public void insertEntries(List<OrderEntryModel> entries) {
        orderEntryMapper.insertList(entries);
    }
}
