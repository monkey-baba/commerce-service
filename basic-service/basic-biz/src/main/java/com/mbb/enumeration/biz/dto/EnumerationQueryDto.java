package com.mbb.enumeration.biz.dto;

import lombok.Data;

@Data
public class EnumerationQueryDto {

    private String id;
    private String version;
    private String code;
    private String name;
    private String offset;
}
