package com.mbb.basic.biz.dto;

import lombok.Data;

@Data
public class DictionaryQueryDto {
    private Integer page;
    private Integer pageSize;
    private String code;
    private String name;
    private String offset;
}
