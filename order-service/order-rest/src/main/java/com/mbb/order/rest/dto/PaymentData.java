package com.mbb.order.rest.dto;

import java.util.Date;
import lombok.Data;

@Data
public class PaymentData {
    private String type;
    private Double amount;
}
