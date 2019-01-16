package com.mbb.customer.biz.service.impl;

import com.lxm.idgenerator.service.intf.IdService;
import com.mbb.customer.biz.dao.CustomerMapper;
import com.mbb.customer.biz.dto.CustomerInfoDto;
import com.mbb.customer.biz.dto.CustomerQueryDto;
import com.mbb.customer.biz.enumation.CustomerStatus;
import com.mbb.customer.biz.model.CustomerModel;
import com.mbb.customer.biz.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
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

    private static final Logger logger = LogManager.getLogger(CustomerServiceImpl.class);

    private static final Integer limit = 25;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private IdService idService;


    @Override
    public List<CustomerInfoDto> getCustomers(CustomerQueryDto customerQueryDto) {
        //封装查询Example
        Example example = mapQueryInfo(customerQueryDto);
        //封装分页参数
        RowBounds rowBounds = mapRowBounds(customerQueryDto);
        List<CustomerModel> customerModels = customerMapper.selectByExampleAndRowBounds(example, rowBounds);
        logger.info("customer size====" + customerModels.size());
        //处理返回结果
        return dealResult(customerModels);
    }

    @Override
    public void addCustomer(List<CustomerInfoDto> customerInfoDtoList) {
        if (!CollectionUtils.isEmpty(customerInfoDtoList)) {
            for (CustomerInfoDto customerInfoDto : customerInfoDtoList) {
                CustomerModel customerModel = new CustomerModel();
                customerModel.setId(idService.genId());
                customerModel.setCode(customerInfoDto.getCode());
                customerModel.setName(customerInfoDto.getName());
                customerModel.setPhone(customerInfoDto.getPhone());
                customerModel.setEmail(customerModel.getEmail());
                customerModel.setStatus(CustomerStatus.DISABLED);
                customerModel.setVersion(1);
                customerMapper.insert(customerModel);
            }
        }
    }

    @Override
    public void deleteCustomer(String id) {
        customerMapper.deleteByPrimaryKey(id);
    }

    private List<CustomerInfoDto> dealResult(List<CustomerModel> customerModels) {
        List<CustomerInfoDto> customerInfoDtoList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(customerModels)) {
            for (CustomerModel customerModel : customerModels) {
                CustomerInfoDto customerInfoDto = new CustomerInfoDto();
                //主键
                customerInfoDto.setId(String.valueOf(customerModel.getId()));
                //客户编号
                customerInfoDto.setCode(customerModel.getCode());
                //客户名称
                customerInfoDto.setName(customerModel.getName());
                //手机号
                customerInfoDto.setPhone(customerModel.getPhone());
                //邮箱
                customerInfoDto.setEmail(customerModel.getEmail());
                //状态
                CustomerStatus status = customerModel.getStatus();
//                customerInfoDto.setStatus();
                customerInfoDtoList.add(customerInfoDto);
            }
        }
        return customerInfoDtoList;
    }

    private Example mapQueryInfo(CustomerQueryDto customerQueryDto) {
        //客户编号
        String code = customerQueryDto.getCode();
        //客户姓名
        String name = customerQueryDto.getName();
        //手机号
        String phone = customerQueryDto.getPhone();
        Example example = new Example(CustomerModel.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(code)) {
            criteria.andLike("code", "%" + code + "%");
        }
        if (StringUtils.isNotBlank(name)) {
            criteria.andLike("name", "%" + name + "%");
        }
        if (StringUtils.isNotBlank(phone)) {
            criteria.andEqualTo("phone", phone);
        }
        return example;
    }

    private RowBounds mapRowBounds(CustomerQueryDto customerQueryDto) {
        String queryOffset = customerQueryDto.getOffset();
        Integer offset = StringUtils.isBlank(queryOffset) ? 0 : Integer.valueOf(queryOffset);
        RowBounds rowBounds = new RowBounds(offset, limit);
        return rowBounds;
    }
}
