package com.mbb.customer.biz.service.impl;

import com.mbb.customer.biz.dao.CustomerMapper;
import com.mbb.customer.biz.model.CustomerModel;
import com.mbb.customer.biz.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author lf
 * @create 2019-01-14 18:01
 */
@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public CustomerModel findById(Long id) {
        return customerMapper.selectByPrimaryKey(id);
    }

    @Override
    public CustomerModel findByCode(String code) {
        Example example = new Example(CustomerModel.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("code", code);
        return customerMapper.selectOneByExample(example);
    }


    @Override
    public List<CustomerModel> getCustomers(CustomerModel customerModel) {
        //封装查询Example
        Example example = mapQueryInfo(customerModel);
        List<CustomerModel> customerModels = customerMapper.selectByExample(example);
        log.info("customer size====" + customerModels.size());
        return customerModels;
    }

    @Override
    public void createStock(CustomerModel customerModel) {
        if (customerModel != null) {
            customerMapper.insert(customerModel);
        }
    }

    @Override
    public void deleteCustomer(String code) {
        log.info("code==" + code);
        CustomerModel customerModel = this.findByCode(code);
        customerMapper.deleteByPrimaryKey(customerModel);
    }

    @Override
    public void updateCustomer(CustomerModel customerModel) {
        this.customerMapper.updateByPrimaryKey(customerModel);
    }

    private Example mapQueryInfo(CustomerModel customerModel) {
        //客户编号
        String code = customerModel.getCode();
        //客户姓名
        String name = customerModel.getName();
        //手机号
        String phone = customerModel.getPhone();
        Example example = new Example(CustomerModel.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(code)) {
            criteria.andLike("code", code + "%");
        }
        if (StringUtils.isNotBlank(name)) {
            criteria.andLike("name", name + "%");
        }
        if (StringUtils.isNotBlank(phone)) {
            criteria.andLike("phone", phone + "%");
        }
        return example;
    }

}
