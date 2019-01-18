package com.mbb.auth.rest.dto.req;

import lombok.Data;

@Data
public class RoleListQuery {

    private Integer pageNum;
    private Integer pageSize;
    private String code;
    private String name;
}
