package com.mbb.customer.api;

import com.github.pagehelper.PageInfo;
import com.mbb.customer.common.dto.CustomerData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author lf
 * @create 2019-01-25 11:41
 */
@FeignClient(name = "customer")
public interface CustomerApi {

    /**
     * 根据客户编码和客户名称模糊搜索客户信息
     *
     * @return result
     */
    @GetMapping("/api/v1/customer/list")
    PageInfo<CustomerData> getCustomers(@RequestParam("code") String code, @RequestParam("name") String name,
                                        @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);

    @GetMapping("/api/v1/customer/data")
    CustomerData getCustomerData(@RequestParam("id") Long id);
}
