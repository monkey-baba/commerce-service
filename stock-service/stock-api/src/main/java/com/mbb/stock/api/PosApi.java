package com.mbb.stock.api;

import com.github.pagehelper.PageInfo;
import com.mbb.stock.common.dto.PosDetailData;
import com.mbb.stock.common.dto.StoreDetailData;
import com.mbb.stock.common.dto.StoreInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 用于声明消费者消费自己的服务
 */
@FeignClient(name = "stock")
public interface PosApi {


    @GetMapping("/api/v1/pos/detail")
    PosDetailData getPosDetailById(@RequestParam("id") Long id);

}
