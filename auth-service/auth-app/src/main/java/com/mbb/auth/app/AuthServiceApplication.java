package com.mbb.auth.app;

import org.springframework.http.ResponseEntity;
import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import xin.yuki.auth.boot.EnableCloudAuthServer;


@SpringBootApplication(scanBasePackages = "com.mbb.auth")
@EnableDiscoveryClient
@MapperScan(basePackages = "com.mbb.auth.biz.dao")
@EnableCloudAuthServer
public class AuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }

}
