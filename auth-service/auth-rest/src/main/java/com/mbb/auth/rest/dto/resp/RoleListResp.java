package com.mbb.auth.rest.dto.resp;

import java.util.List;
import lombok.Data;

@Data
public class RoleListResp {
    private Long id;
    private String code;
    private String name;
    private String description;
    private List<String> parents;
    private List<String> children;
}
