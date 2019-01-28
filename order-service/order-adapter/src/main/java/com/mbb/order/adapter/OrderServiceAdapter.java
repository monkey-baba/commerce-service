package com.mbb.order.adapter;

import com.github.pagehelper.PageInfo;
import com.mbb.basic.api.DictValueApi;
import com.mbb.basic.common.dto.DictValueData;
import com.mbb.customer.api.CustomerApi;
import com.mbb.customer.common.dto.CustomerData;
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

    @Autowired
    private CustomerApi customerApi;

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

    public List<DictValueData> getPlatforms() {
        return dictValueApi.getPlatform();
    }

    public DictValueData getDictValue(Long id) {
        return dictValueApi.getDictValue(id);
    }

    public List<DictValueData> getDeliveryTypes() {
        return dictValueApi.getDeliveryType();
    }

    public List<DictValueData> getCarriers() {
        return dictValueApi.getCarrier();
    }

    public List<DictValueData> getInvoiceTypes() {
        return dictValueApi.getInvoiceType();
    }

    public PageInfo<CustomerData> getCustomers(String code, String name, Integer pageNum, Integer pageSize) {
        return customerApi.getCustomers(code, name, pageNum, pageSize);
    }

    public String getPosNameById(Long id) {
        // TODO: 2019/1/25 门店api提供出来替换
        return "大华仓";
    }

    public List<DictValueData> getSkuSpecs() {
        return dictValueApi.getSkuSpec();
    }
}
