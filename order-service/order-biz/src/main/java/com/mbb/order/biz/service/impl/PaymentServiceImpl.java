package com.mbb.order.biz.service.impl;

import com.mbb.order.biz.dao.PaymentMapper;
import com.mbb.order.biz.model.PaymentModel;
import com.mbb.order.biz.service.PaymentService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentMapper paymentMapper;

    @Override
    public void insertPayments(List<PaymentModel> payments) {
        paymentMapper.insertList(payments);
    }
}
