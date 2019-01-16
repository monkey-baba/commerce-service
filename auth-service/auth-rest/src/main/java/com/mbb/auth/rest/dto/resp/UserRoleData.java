package com.mbb.auth.rest.dto.resp;

import lombok.Data;

@Data
public class UserRoleData {

    private String label;
    private Long key;
    private Boolean exists;
}
