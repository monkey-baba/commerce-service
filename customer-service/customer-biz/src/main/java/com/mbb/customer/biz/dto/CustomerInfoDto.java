package com.mbb.customer.biz.dto;

import lombok.Data;

@Data
public class CustomerInfoDto {
    private String id;
    private String code;
    private String name;
    private String phone;
    private String email;
    private String status;

}
