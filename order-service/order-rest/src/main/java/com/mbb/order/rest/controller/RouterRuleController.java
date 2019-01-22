package com.mbb.order.rest.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mbb.basic.common.dto.DictValueData;
import com.mbb.order.adapter.OrderServiceAdapter;
import com.mbb.order.biz.model.RouterRuleModel;
import com.mbb.order.biz.service.RouterRuleService;
import com.mbb.order.rest.dto.RouterRuleBaseData;
import com.mbb.order.rest.dto.RouterRuleListQuery;
import com.mbb.order.rest.dto.RouterRuleUpdateData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author hyx
 * @title RouterRuleController
 * @description
 * @date 2019/1/16
 */
@RestController
@RequestMapping("/api/v1/routerrule")
public class RouterRuleController extends BaseController {

    @Autowired
    private RouterRuleService routerRuleService;

    @Autowired
    private OrderServiceAdapter orderServiceAdapter;

    @GetMapping("/search")
    public ResponseEntity search(RouterRuleListQuery routerRuleListQuery) {
        PageHelper.startPage(routerRuleListQuery.getPageNum(), routerRuleListQuery.getPageSize());
        List<RouterRuleModel> rules = routerRuleService.getRules();
        PageInfo<RouterRuleModel> origin = PageInfo.of(rules);
        List<RouterRuleBaseData> convertedList = origin.getList().stream().map(source -> {
            RouterRuleBaseData target = new RouterRuleBaseData();
            convert(source, target);
            return target;
        }).collect(Collectors.toList());
        PageInfo<RouterRuleBaseData> result = PageInfo.of(convertedList);
        result.setTotal(origin.getTotal());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/update")
    public ResponseEntity update(@RequestBody RouterRuleUpdateData updateData) {
        List<RouterRuleBaseData> data = updateData.getData();
        Map<String, Object> params = new HashMap<>(4);
        params.put("modifyUserId", updateData.getModifyUserId());
        params.put("modifyDate", new Date());
        for (RouterRuleBaseData datum : data) {
            params.put("enabled", datum.getEnabled());
            params.put("priority", datum.getPriority());
            routerRuleService.update(datum.getId(), params);
        }
        return ResponseEntity.ok("更新成功");
    }

    private void convert(RouterRuleModel source, RouterRuleBaseData target) {
        target.setId(source.getId());
        target.setCode(source.getCode());
        target.setName(source.getName());
        target.setEnabled(source.getEnabled());
        target.setPriority(source.getPriority());
        DictValueData routerRuleTypeValue = orderServiceAdapter.getRouterRuleTypeValue(source.getTypeId());
        if (routerRuleTypeValue != null) {
            target.setRouterRuleDisplayName(routerRuleTypeValue.getName());
        }
        Map<String, String> userInfo = orderServiceAdapter.getUserInfo(source.getModifyUserId());
        if (userInfo != null) {
            target.setModifyUserName(userInfo.get("name"));
        }
        target.setModifyDate(source.getModifyDate());
    }
}
