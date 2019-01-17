package com.mbb.customer.rest.dto;

import lombok.Data;

@Data
public class CustomerInfoResp {
    private Long id;
    private String code;
    private String name;
    private String phone;
    private String email;
    private Long statusId;

}
