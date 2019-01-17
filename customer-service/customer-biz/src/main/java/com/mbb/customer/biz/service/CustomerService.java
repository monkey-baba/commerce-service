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
     * @param code
     */
    void deleteCustomer(String code);

    /**
     * 根据id查找客户
     *
     * @param id
     * @return
     */
    CustomerModel findById(Long id);

    /**
     * 更新客户
     *
     * @param customerModel
     */
    void updateCustomer(CustomerModel customerModel);

}
