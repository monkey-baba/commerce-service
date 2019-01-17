package com.mbb.auth.rest.dto.resp;

import java.util.List;
import lombok.Data;

@Data
public class ChangeRoleResp {
    private List<String> roleNames;
}
