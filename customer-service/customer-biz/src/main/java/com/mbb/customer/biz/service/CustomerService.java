package com.mbb.customer.biz.service;

import com.mbb.customer.biz.model.CustomerModel;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author lf
 * @create 2019-01-08 15:37
 */
public interface CustomerService {

    /**
     * 查询客户
     *
     * @param customerModel
     * @return
     */
    List<CustomerModel> getCustomers(CustomerModel customerModel);

    /**
     * 新增客户
     *
     * @param customerModel
     */
    void createStock(CustomerModel customerModel);

    /**
     * 删除客户
     *
     * @param id
     */
    void deleteCustomer(String id);

}
