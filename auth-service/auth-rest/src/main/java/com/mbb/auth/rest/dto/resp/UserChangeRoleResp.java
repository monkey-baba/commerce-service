package com.mbb.auth.rest.dto.resp;

import java.util.List;
import lombok.Data;

@Data
public class UserChangeRoleResp {
    private List<String> roleNames;
}
