package com.mbb.basic.rest.controller;


import com.mbb.basic.biz.service.LocalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/basic/api/v1/local")
public class LocalController extends BaseController {

    @Autowired
    private LocalService localService;

    @GetMapping("/basic")
    public String basic() {
        return localService.local();
    }

}
