package com.mbb.order.adapter;

import com.mbb.basic.api.DictValueApi;
import com.mbb.basic.common.dto.DictValueData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author hyx
 * @title OrderServiceAdapter
 * @description
 * @date 2019/1/22
 */
@Component
public class OrderServiceAdapter {

    @Autowired
    private DictValueApi dictValueApi;

    /**
     * 根据id获取路由规则枚举值
     *
     * @param id 枚举值id
     * @return 枚举数据
     */
    public DictValueData getRouterRuleTypeValue(Long id) {
        return dictValueApi.getDictValue(id);
    }

    /**
     * 根据id获得用户信息
     *
     * @param id 用户id
     * @return 用户信息
     */
    public Map<String, String> getUserInfo(Long id) {
        return Collections.singletonMap("name", "管理员");
    }

    /**
     * 获取订单状态
     *
     * @return
     */
    public List<DictValueData> getOrderStatus() {
        return dictValueApi.getOrderStatus();
    }

    /**
     * 获取订单类型
     *
     * @return
     */
    public List<DictValueData> getOrderType() {
        return dictValueApi.getOrderType();
    }

    /**
     * 获取网店
     *
     * @return
     */
    public List<DictValueData> getBaseStores() {
        return dictValueApi.getBaseStore();
    }

    public DictValueData getDictValue(Long id) {
        return dictValueApi.getDictValue(id);
    }
}
