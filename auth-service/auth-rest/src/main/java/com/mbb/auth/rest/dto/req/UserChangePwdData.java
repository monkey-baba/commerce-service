package com.mbb.auth.rest.dto.req;

import lombok.Data;

@Data
public class UserChangePwdData {
    private String username;
    private String password;
}
