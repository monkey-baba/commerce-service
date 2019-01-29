package com.mbb.order.biz.service;

import com.mbb.order.biz.model.PaymentModel;
import java.util.List;

public interface PaymentService {

    void insertPayments(List<PaymentModel> payments);
}
