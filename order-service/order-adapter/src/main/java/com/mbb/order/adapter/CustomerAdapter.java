package com.mbb.order.adapter;

import com.github.pagehelper.PageInfo;
import com.mbb.customer.api.CustomerApi;
import com.mbb.customer.common.dto.CustomerData;
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

    public PageInfo<CustomerData> getCustomers(String code, String name, Integer pageNum,
            Integer pageSize) {
        return customerApi.getCustomers(code, name, pageNum, pageSize);
    }

    public String getCustomerName(Long customerId) {
        CustomerData data = customerApi.getCustomerData(customerId);
        if (data != null) {
            return data.getName();
        }
        return String.valueOf(customerId);
    }
}
