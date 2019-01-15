package com.mbb.auth.rest.dto.resp;

import java.util.List;
import lombok.Data;

@Data
public class UserLoginResp {

    private Long id;
    private String username;
    private String name;
    private List<String> roles;
    private List<String> authorities;

}
