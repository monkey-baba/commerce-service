package com.mbb.auth.rest.dto.req;

import lombok.Data;

@Data
public class RoleCreateData {

    private String code;
    private String name;
    private String description;

}
