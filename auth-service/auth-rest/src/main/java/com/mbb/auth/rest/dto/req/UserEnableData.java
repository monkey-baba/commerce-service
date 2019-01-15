package com.mbb.auth.rest.dto.req;

import lombok.Data;

@Data
public class UserEnableData {

    private String username;

    private Boolean enabled;

}
