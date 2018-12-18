package com.mbb.commerce.demo.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 用于声明消费者消费自己的服务
 */
@FeignClient(name = "demo-service")
public interface DemoServiceConsumer {

    /**
     * 示例： feign 调用自己的 demo接口
     * @return
     */
    @GetMapping("/api/v1/demo")
    String getDemo();

}
