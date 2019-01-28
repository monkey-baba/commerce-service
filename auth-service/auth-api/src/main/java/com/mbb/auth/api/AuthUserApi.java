package com.mbb.auth.api;

import com.mbb.auth.common.dto.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 用于声明消费者消费自己的服务
 */
@FeignClient(name = "auth")
public interface AuthUserApi {

    /**
     * 示例:调用自己的接口
     * @return result
     */
    @GetMapping("/api/v1/user/get")
    UserInfo getUserInfo(@RequestParam("id")Long id);

}
