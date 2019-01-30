package com.mbb.order.rest.dto;

import lombok.Data;

@Data
public class AddRemarkData {
    private Long orderId;
    private String remark;
}
