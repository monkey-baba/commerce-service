package com.mbb.basic.rest.dto.resp;

import lombok.Data;

@Data
public class DictListResp {
    private Long id;
    private String code;
    private String name;
    private Boolean editable;
}
