package com.mbb.order.biz.service.impl;

import com.mbb.order.biz.dao.RouterRuleMapper;
import com.mbb.order.biz.model.RouterRuleModel;
import com.mbb.order.biz.service.RouterRuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author hyx
 * @title RouterRuleServiceImpl
 * @description
 * @date 2019/1/16
 */
@Service
@Slf4j
public class RouterRuleServiceImpl implements RouterRuleService {

    @Autowired
    private RouterRuleMapper routerRuleMapper;

    @Override
    public List<RouterRuleModel> getRules() {
        return routerRuleMapper.selectAll();
    }

    @Override
    public void update(Long id, Map<String, Object> parameters) {
        RouterRuleModel routerRuleModel = routerRuleMapper.selectByPrimaryKey(id);
        routerRuleModel.setEnabled((Boolean) parameters.get("enabled"));
        routerRuleModel.setPriority((Integer) parameters.get("priority"));
        routerRuleModel.setModifyDate((Date) parameters.get("modifyDate"));
        routerRuleModel.setModifyUserId((Long) parameters.get("modifyUserId"));
        routerRuleMapper.updateByPrimaryKey(routerRuleModel);
    }

}
