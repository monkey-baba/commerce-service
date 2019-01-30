package com.mbb.stock.rest.controller;

import com.mbb.basic.common.dto.AddressData;
import com.mbb.stock.adapter.PosAddressAdapter;
import com.mbb.stock.adapter.PosServiceAdapter;
import com.mbb.stock.biz.model.PointOfServiceModel;
import com.mbb.stock.biz.service.PosService;
import com.mbb.stock.common.dto.PosDetailData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/pos")
public class PosController {

    @Autowired
    private PosService posService;

    @Autowired
    private PosServiceAdapter posServiceAdapter;

    @Autowired
    private PosAddressAdapter posAddressAdapter;

    @GetMapping("/detail")
    public ResponseEntity getPosDetail(@RequestParam Long id){
        PointOfServiceModel pos = posService.findById(id);
        PosDetailData data=new PosDetailData();
        data.setCode(pos.getCode());
        data.setContact(pos.getContact());
        data.setName(pos.getName());
        data.setOwner(pos.getOwner());
        data.setStatus(posServiceAdapter.getDictValueName(pos.getStatusId()));
        data.setClassify(posServiceAdapter.getDictValueName(pos.getClassifyId()));
        AddressData address = posAddressAdapter.getAddress(pos.getAddressId());
        data.setDetailAddress(address.getDetail());
        data.setPcd(address.getAddress());
        return ResponseEntity.ok(data);
    }
}
