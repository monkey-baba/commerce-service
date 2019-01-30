package com.mbb.order.biz.service;

import com.mbb.order.biz.model.InvoiceModel;

public interface InvoiceService {

    void insert(InvoiceModel invoice);

    InvoiceModel getInvoiceByOrderId(Long id);
}
