package com.mbb.auth.rest.controller;


import com.mbb.auth.biz.service.LocalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/auth/api/v1/local")
public class LocalController extends BaseController {

    @Autowired
    private LocalService localService;

    @GetMapping("/auth")
    public String auth() {
        return localService.local();
    }

}
