package com.mbb.customer.rest.controller;


import com.mbb.customer.biz.service.LocalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/customer/api/v1/local")
public class LocalController extends BaseController {

    @Autowired
    private LocalService localService;

    @GetMapping("/customer")
    public String customer() {
        return localService.local();
    }

}
