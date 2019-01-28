package com.mbb.basic.biz.service;

import com.mbb.basic.biz.model.AddressModel;

public interface AddressService {

    int createAddress(AddressModel address);

    int updateAddress(AddressModel address);

    AddressModel findById(Long id);
}
