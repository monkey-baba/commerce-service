package com.mbb.order.rest.dto;

import lombok.Data;

/**
 * @author hyx
 * @title RouterRuleQuery
 * @description
 * @date 2019/1/16
 */
@Data
public class RouterRuleListQuery {

    /**
     * 页数
     */
    private Integer pageNum;

    /**
     * 每页记录数
     */
    private Integer pageSize;
}
