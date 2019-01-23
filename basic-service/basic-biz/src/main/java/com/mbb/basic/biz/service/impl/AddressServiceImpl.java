package com.mbb.basic.biz.service.impl;

import com.mbb.basic.biz.dao.AddressMapper;
import com.mbb.basic.biz.model.AddressModel;
import com.mbb.basic.biz.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressMapper addressMapper;

    @Override
    public int insert(AddressModel address) {
        return addressMapper.insert(address);
    }

    @Override
    public AddressModel findById(Long id) {
        return addressMapper.selectByPrimaryKey(id);
    }
}
