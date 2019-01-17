package com.mbb.auth.rest.dto.req;

import java.util.List;
import lombok.Data;

@Data
public class GroupChangeRoleData {
    private Long groupId;
    private List<Long> roles;
}
