package com.mbb.order.rest.dto;

import lombok.Data;

@Data
public class ConsignmentData {
    private String code;
    private String status;
    private String carrier;
    private String expressNum;
}
