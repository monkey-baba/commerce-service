package com.mbb.order.rest.dto;

import lombok.Data;

@Data
public class InvoiceData {

    private Boolean applied;
    private String type;
    private String title;
    private Double amount;
}
