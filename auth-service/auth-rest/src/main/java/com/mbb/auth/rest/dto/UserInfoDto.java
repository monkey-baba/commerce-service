package com.mbb.auth.rest.dto;

import java.util.List;
import lombok.Data;

@Data
public class UserInfoDto {

    private String username;
    private String name;
    private List<String> roles;
    private List<String> authorities;

}
