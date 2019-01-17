package com.mbb.dictionaryvalue.biz.dto;

import lombok.Data;

@Data
public class DictionaryValueQueryDto {

    private String id;
    private String version;
    private String code;
    private String name;
    private String offset;
}
