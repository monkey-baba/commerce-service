package com.mbb.basic.common.dto;

import lombok.Data;

@Data
public class DictValueData {

    private Long id;
    private String code;
    private String name;
    private String description;
    private String attribute1;
    private String attribute2;
    private String attribute3;
    private Boolean active;
}
