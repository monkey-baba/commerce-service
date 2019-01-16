package com.mbb.customer.rest.dto;

import lombok.Data;

/**
 * ${DESCRIPTION}
 *
 * @author lf
 * @create 2019-01-16 17:25
 */
@Data
public class CustomerCreateData {
    private String code;
    private String name;
    private String phone;
    private String email;
    private Long statusId;
}
