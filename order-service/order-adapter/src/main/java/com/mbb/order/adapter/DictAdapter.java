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
public class DictAdapter {

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

    public List<DictValueData> getSkuSpecs() {
        return dictValueApi.getSkuSpec();
    }

    public List<DictValueData> getPaymentTypes() {
        return dictValueApi.getPaymentType();
    }

    /**
     * 获取配货单状态
     *
     * @return
     */
    public List<DictValueData> getConsignmentStatus() {
        return dictValueApi.getConsignmentStatus();
    }
}
