package com.mbb.customer.rest.dto;

import lombok.Data;

/**
 * ${DESCRIPTION}
 *
 * @author lf
 * @create 2019-01-17 15:20
 */
@Data
public class CustomerUpdateData {
    private Long id;
    private String code;
    private String name;
    private String phone;
    private String email;
    private Long statusId;
}
