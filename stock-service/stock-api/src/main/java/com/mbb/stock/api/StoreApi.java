package com.mbb.stock.api;

import com.github.pagehelper.PageInfo;
import com.mbb.stock.common.dto.StoreInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 用于声明消费者消费自己的服务
 */
@FeignClient(name = "stock")
public interface StoreApi {

    /**
     * 示例:调用自己的接口
     *
     * @return result
     */
    @GetMapping("/api/v1/store/info")
    PageInfo<StoreInfoDto> getStores(@RequestParam("name") String name,
            @RequestParam("code") String code,
            @RequestParam("type") String type,
            @RequestParam("owner") String owner,
            @RequestParam("classification") String classification,
            @RequestParam("people") String people,
            @RequestParam("status") String status,
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize);

}
