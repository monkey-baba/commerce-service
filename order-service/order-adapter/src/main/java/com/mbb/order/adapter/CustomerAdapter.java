package com.mbb.order.adapter;

import com.github.pagehelper.PageInfo;
import com.mbb.basic.api.DictValueApi;
import com.mbb.basic.common.dto.DictValueData;
import com.mbb.customer.api.CustomerApi;
import com.mbb.customer.common.dto.CustomerData;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author hyx
 * @title OrderServiceAdapter
 * @description
 * @date 2019/1/22
 */
@Component
public class CustomerAdapter {


    @Autowired
    private CustomerApi customerApi;

    public PageInfo<CustomerData> getCustomers(String code, String name, Integer pageNum, Integer pageSize) {
        return customerApi.getCustomers(code, name, pageNum, pageSize);
    }

}
