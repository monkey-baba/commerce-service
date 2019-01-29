package com.mbb.order.adapter;

import com.mbb.basic.api.AddressApi;
import com.mbb.basic.common.dto.AddressData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ${DESCRIPTION}
 *
 * @author lf
 * @create 2019-01-23 15:40
 */
@Component
public class AddressAdapter {

    @Autowired
    private AddressApi addressApi;

    public AddressData getAddress(Long id) {
        return addressApi.getAddress(id);
    }

    public Long saveAddress(AddressData addrData) {
        return addressApi.saveAddress(addrData);
    }
}
