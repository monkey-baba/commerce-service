package com.mbb.auth.rest.dto.resp;

import java.util.List;
import lombok.Data;

@Data
public class GroupListResp {
    private Long id;
    private String code;
    private String name;
    private String description;
    private List<String> roles;
}
