package com.mbb.basic.biz.dto;

import lombok.Data;

@Data
public class DictionaryQueryDto {

    private String id;
    private String version;
    private String code;
    private String name;
    private String offset;
}
