package com.mbb.basic.rest.controller;

import com.lxm.idgenerator.service.intf.IdService;
import com.mbb.basic.biz.model.AddressModel;
import com.mbb.basic.biz.service.AddressService;
import com.mbb.basic.common.dto.AddressData;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/address")
public class AddressController extends BaseController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private IdService idService;

    @PostMapping("/save")
    public ResponseEntity saveAddress(AddressData data) {
        if (CollectionUtils.isEmpty(data.getAddress()) || data.getAddress().size() != 3) {
            return ResponseEntity.badRequest().body("地址数据不完整");
        }
        AddressModel address = new AddressModel();
        address.setId(idService.genId());
        address.setAddress(data.getDetail());
        address.setProvince(data.getAddress().get(0));
        address.setCity(data.getAddress().get(1));
        address.setDistrict(data.getAddress().get(2));
        address.setPhone(data.getPhone());
        int insert = addressService.createAddress(address);
        if (insert != 1) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("保存失败");
        }
        return ResponseEntity.ok(address.getId());
    }

    @GetMapping("/get")
    public ResponseEntity getAddress(Long id) {
        AddressModel address = addressService.findById(id);
        if (address == null) {
            return ResponseEntity.badRequest().body("未找到数据");
        }
        AddressData data = new AddressData();
        data.setAddress(
                Arrays.asList(address.getProvince(), address.getCity(), address.getDistrict()));
        data.setDetail(address.getAddress());
        data.setPhone(address.getPhone());
        return ResponseEntity.ok(data);
    }

}
