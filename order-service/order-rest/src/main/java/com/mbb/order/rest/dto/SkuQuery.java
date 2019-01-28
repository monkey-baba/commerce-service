package com.mbb.order.rest.dto;

import lombok.Data;

/**
 * ${DESCRIPTION}
 *
 * @author lf
 * @create 2019-01-25 13:34
 */
@Data
public class SkuQuery {
    private String code;
    private String name;
    private Integer pageNum;
    private Integer pageSize;
}
