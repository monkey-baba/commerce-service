package com.mbb.customer.rest.dto;

import lombok.Data;

@Data
public class CustomerListQuery {
    private String code;
    private String name;
    private String phone;
    private Integer pageNum;
    private Integer pageSize;
}
