package com.mbb.auth.rest.dto.resp;

import java.util.List;
import lombok.Data;

@Data
public class UserChangeGroupResp {
    private List<String> groupNames;
    private List<String> roleNames;
}
