package com.mbb.auth.rest.dto.req;

import java.util.List;
import lombok.Data;

@Data
public class RoleChangeRoleData {
    private Long roleId;
    private List<Long> roles;
}
