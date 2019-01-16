package com.mbb.auth.rest.dto.req;

import java.util.List;
import lombok.Data;

@Data
public class UserChangeGroupData {
    private Long userId;
    private List<Long> groups;
}
