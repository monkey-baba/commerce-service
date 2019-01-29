package com.mbb.order.biz.service.impl;

import com.mbb.order.biz.dao.InvoiceMapper;
import com.mbb.order.biz.model.InvoiceModel;
import com.mbb.order.biz.service.InvoiceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceMapper invoiceMapper;


    @Override
    public void insert(InvoiceModel invoice) {
        invoiceMapper.insert(invoice);
    }
}
