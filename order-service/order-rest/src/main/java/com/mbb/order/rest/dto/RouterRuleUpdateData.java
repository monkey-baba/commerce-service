package com.mbb.order.rest.dto;

import lombok.Data;

import java.util.List;

/**
 * @author hyx
 * @title RouterRuleUpdateData
 * @description
 * @date 2019/1/18
 */
@Data
public class RouterRuleUpdateData {

    /**
     * 需要修改的数据
     */
    private List<RouterRuleBaseData> data;

    /**
     * 当前用户Id
     */
    private Long modifyUserId;
}
