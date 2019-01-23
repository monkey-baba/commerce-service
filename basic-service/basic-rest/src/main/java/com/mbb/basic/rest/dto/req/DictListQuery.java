package com.mbb.basic.rest.dto.req;

import lombok.Data;

@Data
public class DictListQuery {
    private Integer pageNum;
    private Integer pageSize;
    private String code;
    private String name;
}
