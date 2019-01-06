package com.mbb.stock.rest.controller;


import com.mbb.stock.biz.service.LocalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/stock/api/v1/local")
public class LocalController extends BaseController {

    @Autowired
    private LocalService localService;

    @GetMapping("/stock")
    public String stock() {
        return localService.local();
    }

}
