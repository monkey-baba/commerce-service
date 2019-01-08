package com.mbb.promotion.rest.controller;


import com.mbb.promotion.biz.service.LocalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/v1")
public class LocalController extends BaseController {

    @Autowired
    private LocalService localService;

    @GetMapping("/promotion")
    public String promotion() {
        return localService.local();
    }

}
