package com.mbb.auth.rest.dto;

import lombok.Data;

@Data
public class UserListQuery {

    private Integer pageNum;
    private Integer pageSize;
    private String username;
    private String name;
    private String mobileNumber;
}
