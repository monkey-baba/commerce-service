package com.mbb.customer.rest.controller;

import com.mbb.customer.biz.dto.CustomerInfoDto;
import com.mbb.customer.biz.dto.CustomerQueryDto;
import com.mbb.customer.biz.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author lf
 * @create 2019-01-08 15:33
 */
@RestController
@RequestMapping("api/v1/customer")
public class CustomerController extends BaseController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/info")
    public ResponseEntity getCustomers(@RequestBody CustomerQueryDto customerQueryDto) {
        List<CustomerInfoDto> customerInfoDtoList = customerService.getCustomers(customerQueryDto);
        return ResponseEntity.ok(customerInfoDtoList);
    }

    @PostMapping("/add")
    public ResponseEntity addStock(@RequestBody List<CustomerInfoDto> customerInfoDtoList) {
        customerService.addCustomer(customerInfoDtoList);
        return ResponseEntity.ok(Boolean.TRUE);
    }

    @GetMapping("/delete")
    public ResponseEntity deleteCustomer(@RequestParam(value = "id", required = true) String id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok(Boolean.TRUE);
    }
}
