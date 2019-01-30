package com.mbb.product.api;

import com.github.pagehelper.PageInfo;
import com.mbb.product.common.dto.SkuData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@FeignClient(name = "product")
public interface ProductApi {


    @GetMapping("/api/v1/sku/page")
    PageInfo<SkuData> getSkuPage(@RequestParam("code") String code, @RequestParam("name") String name,
            @RequestParam("pageNum") Integer pageNum,@RequestParam("pageSize") Integer pageSize);

    @GetMapping("/api/v1/sku/getById")
    SkuData getSkuById(@RequestParam("id") Long id);
}
