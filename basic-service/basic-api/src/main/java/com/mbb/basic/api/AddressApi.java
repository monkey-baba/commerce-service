package com.mbb.basic.api;

import com.mbb.basic.common.dto.AddressData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "basic")
public interface AddressApi {

    @PostMapping("/api/v1/address/save")
    Long saveAddress(AddressData data);

    @GetMapping("/api/v1/address/get")
    AddressData saveAddress(Long id);
}
