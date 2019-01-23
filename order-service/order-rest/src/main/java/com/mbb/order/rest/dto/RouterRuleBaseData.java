package com.mbb.order.rest.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author hyx
 * @title RouterRuleBaseData
 * @description
 * @date 2019/1/16
 */
@Data
public class RouterRuleBaseData {

    private Long id;
    private String code;
    private String name;
    private Boolean enabled;
    private Integer priority;
    private String routerRuleDisplayName;
    private String modifyUserName;
    private Date modifyDate;

}
