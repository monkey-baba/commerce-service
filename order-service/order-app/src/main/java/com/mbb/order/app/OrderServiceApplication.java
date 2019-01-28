package com.mbb.order.app;

import org.springframework.cloud.openfeign.EnableFeignClients;
import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableFeignClients(basePackages = {"com.mbb.basic.api", "com.mbb.customer.api"})
@SpringBootApplication(scanBasePackages = "com.mbb.order")
@EnableDiscoveryClient
@MapperScan(basePackages = "com.mbb.order.biz.dao")
public class OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

}
