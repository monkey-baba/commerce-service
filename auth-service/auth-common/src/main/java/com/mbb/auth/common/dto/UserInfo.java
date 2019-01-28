package com.mbb.auth.common.dto;

import lombok.Data;

@Data
public class UserInfo{
    private Long id;
    private String username;
    private String name;
    private String mobileNumber;
    private String email;
}