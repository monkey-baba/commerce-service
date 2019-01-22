package com.mbb.auth.rest.dto.resp;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class RoleTreeResp {
    private Long id;
    private String label;
    private List<RoleTreeResp> children = new ArrayList<>();
}
