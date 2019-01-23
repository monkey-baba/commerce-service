package com.mbb.product.app;

import org.springframework.cloud.openfeign.EnableFeignClients;
import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication(scanBasePackages = "com.mbb.product")
@EnableDiscoveryClient
@MapperScan(basePackages = "com.mbb.product.biz.dao")
@EnableFeignClients(basePackages = {"com.mbb.basic.api"})
public class ProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }

}
