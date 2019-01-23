package com.mbb.order.biz.service;

import com.mbb.order.biz.model.RouterRuleModel;

import java.util.List;
import java.util.Map;

/**
 * @author hyx
 * @title RouterRuleService
 * @description
 * @date 2019/1/16
 */
public interface RouterRuleService {

    /**
     * 查询路由规则
     *
     * @return 查询结果
     */
    List<RouterRuleModel> getRules();


    /**
     * 更新路由规则
     *
     * @param id         更新的id
     * @param parameters 更新属性
     */
    void update(Long id, Map<String, Object> parameters);
}
