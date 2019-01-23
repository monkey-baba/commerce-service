package com.mbb.product.api;

import com.mbb.product.common.dto.SkuData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@FeignClient(name = "product")
public interface ProductApi {
    /**
     * 根据skuId查询sku信息
     *
     * @return result
     */
    @GetMapping("/api/v1/product/sku/{skuId}")
    SkuData getSku(@PathVariable(name = "skuId") String skuId);

    /**
     * 根据skuId查询sku信息
     *
     * @return result
     */
    @GetMapping("/api/v1/product/sku/list")
    List<SkuData> getSkus(@RequestParam("skuId") String skuId, @RequestParam("skuName") String skuName);

}
