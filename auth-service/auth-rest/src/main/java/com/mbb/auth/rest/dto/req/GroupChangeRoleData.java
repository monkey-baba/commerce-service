package com.mbb.auth.rest.dto.req;

import lombok.Data;
import java.util.List;

@Data
public class GroupChangeRoleData {
    private Long groupId;
    private List<Long> roles;
}
