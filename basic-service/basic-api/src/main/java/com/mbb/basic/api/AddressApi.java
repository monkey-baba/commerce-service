package com.mbb.basic.api;

import com.mbb.basic.common.dto.AddressData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "basic")
public interface AddressApi {

    @PostMapping("/api/v1/address/save")
    Long saveAddress(@RequestBody AddressData data);

    @GetMapping("/api/v1/address/get")
    AddressData getAddress(@RequestParam("id") Long id);
}
