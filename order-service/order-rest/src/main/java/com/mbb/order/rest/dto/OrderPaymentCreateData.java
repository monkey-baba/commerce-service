package com.mbb.order.rest.dto;

import com.mbb.basic.common.dto.DictValueData;
import lombok.Data;

@Data
public class OrderPaymentCreateData {
    private DictValueData type;
    private Double amount;
}
