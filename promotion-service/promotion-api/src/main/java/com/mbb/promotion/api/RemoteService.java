package com.mbb.promotion.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
/**
 * 用于声明消费者消费自己的服务
 */
@FeignClient(name = "promotion-service")
public interface RemoteService {

    /**
     * 示例:调用自己的接口
     * @return result
     */
    @GetMapping("/api/v1/promotion")
    String remotePromotion();

}
