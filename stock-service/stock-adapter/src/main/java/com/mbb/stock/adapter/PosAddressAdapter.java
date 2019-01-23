package com.mbb.stock.adapter;
import com.mbb.basic.api.AddressApi;
import com.mbb.basic.common.dto.AddressData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PosAddressAdapter {
    @Autowired
    private AddressApi addressApi;

    public  Long saveAddress(AddressData data){

        return addressApi.saveAddress(data);
    };
    public  AddressData getAddress(Long id){

        return addressApi.getAddress(id);
    };
}