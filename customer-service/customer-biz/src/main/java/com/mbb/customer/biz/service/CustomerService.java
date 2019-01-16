package com.mbb.customer.biz.service;

import com.mbb.customer.biz.dto.CustomerInfoDto;
import com.mbb.customer.biz.dto.CustomerQueryDto;

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
     * @param customerQueryDto
     * @return
     */
    List<CustomerInfoDto> getCustomers(CustomerQueryDto customerQueryDto);

    /**
     * 新增客户
     *
     * @param customerInfoDtoList
     */
    void addCustomer(List<CustomerInfoDto> customerInfoDtoList);

    /**
     * 删除客户
     *
     * @param id
     */
    void deleteCustomer(String id);
}
