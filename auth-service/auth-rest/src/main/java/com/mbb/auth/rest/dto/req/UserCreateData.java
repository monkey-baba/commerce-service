package com.mbb.auth.rest.dto.req;

import lombok.Data;

@Data
public class UserCreateData {

    private String username;
    private String name;
    private Boolean enabled;
    private String password;
    private String mobileNumber;
    private String email;

}
