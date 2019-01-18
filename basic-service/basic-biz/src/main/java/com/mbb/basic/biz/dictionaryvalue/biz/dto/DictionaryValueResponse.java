package com.mbb.basic.biz.dictionaryvalue.biz.dto;

import lombok.Data;

@Data
public class DictionaryValueResponse {
    private String id;
    private String version;
    private String code;
    private String name;
    private String description;
    private String type_id;
    private String attribute1;
    private String attribute2;
    private String attribute3;
    private Boolean active;
}
