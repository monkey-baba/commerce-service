package com.mbb.oauth.app;

import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import xin.yuki.auth.boot.EnableCloudAuthServer;


@SpringBootApplication(scanBasePackages = "com.mbb.oauth")
@EnableDiscoveryClient
@MapperScan(basePackages = "com.mbb.oauth.biz.dao")
@EnableCloudAuthServer
public class OauthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OauthServiceApplication.class, args);
    }

}
