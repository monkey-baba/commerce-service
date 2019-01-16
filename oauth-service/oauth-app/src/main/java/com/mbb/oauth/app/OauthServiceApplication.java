package com.mbb.oauth.app;

import org.springframework.cloud.openfeign.EnableFeignClients;
import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import xin.yuki.auth.boot.EnableCloudAuthServer;


@SpringBootApplication(scanBasePackages = "com.mbb.oauth")
@EnableDiscoveryClient
@MapperScan(basePackages = "com.mbb.oauth.biz.dao")
@EnableCloudAuthServer
@EnableFeignClients
public class OauthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OauthServiceApplication.class, args);
    }

}
