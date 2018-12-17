package com.mbb.commerce.demo.rest.controller;


import com.mbb.commerce.demo.biz.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController extends BaseController {

    @Autowired
    private DemoService demoService;

    @GetMapping("/demo")
    public String demo() {
        return demoService.demo();
    }

}
