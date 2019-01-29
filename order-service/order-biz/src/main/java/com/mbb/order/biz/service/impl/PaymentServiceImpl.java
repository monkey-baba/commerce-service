package com.mbb.order.biz.service.impl;

import com.mbb.order.biz.dao.PaymentMapper;
import com.mbb.order.biz.model.OrderEntryModel;
import com.mbb.order.biz.model.PaymentModel;
import com.mbb.order.biz.service.PaymentService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Builder;
import tk.mybatis.mapper.util.Sqls;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentMapper paymentMapper;

    @Override
    public void insertPayments(List<PaymentModel> payments) {
        paymentMapper.insertList(payments);
    }

    @Override
    public List<PaymentModel> getPaymentsByOrderId(Long id) {
        Builder builder = Example.builder(PaymentModel.class);
        builder.where(Sqls.custom().andEqualTo("orderId",id));
        return paymentMapper.selectByExample(builder.build());
    }
}
