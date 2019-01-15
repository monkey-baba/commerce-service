package com.mbb.auth.rest.dto.req;

import lombok.Data;

@Data
public class UserUpdateData {
    private Long id;
    private String username;
    private String name;
    private String email;
    private String mobileNumber;
}
