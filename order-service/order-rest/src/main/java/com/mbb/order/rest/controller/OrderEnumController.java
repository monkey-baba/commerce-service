package com.mbb.order.rest.controller;

import com.mbb.basic.common.dto.DictValueData;
import com.mbb.order.adapter.DictAdapter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ${DESCRIPTION}
 *
 * @author lf
 * @create 2019-01-17 18:02
 */
@RestController
@RequestMapping("/api/v1/eunm")
public class OrderEnumController extends BaseController {

    @Autowired
    private DictAdapter orderServiceAdapter;


    @GetMapping("/orderstatus")
    public ResponseEntity getOrderStatus() {
        List<DictValueData> orderStatusDataList = orderServiceAdapter.getOrderStatus();
        return ResponseEntity.ok(orderStatusDataList);
    }


    @GetMapping("/ordertypes")
    public ResponseEntity getOrderType() {
        List<DictValueData> orderTypeDataList = orderServiceAdapter.getOrderType();
        return ResponseEntity.ok(orderTypeDataList);
    }

    @GetMapping("/stores")
    public ResponseEntity getBaseStores() {
        List<DictValueData> baseStoreDataList = orderServiceAdapter.getBaseStores();
        return ResponseEntity.ok(baseStoreDataList);
    }

    @GetMapping("/platforms")
    public ResponseEntity getPlatforms() {
        List<DictValueData> valueDataList = orderServiceAdapter.getPlatforms();
        return ResponseEntity.ok(valueDataList);
    }

    @GetMapping("/deliveryTypes")
    public ResponseEntity getDeliveryTypes() {
        List<DictValueData> valueDataList = orderServiceAdapter.getDeliveryTypes();
        return ResponseEntity.ok(valueDataList);
    }

    @GetMapping("/carriers")
    public ResponseEntity getCarriers() {
        List<DictValueData> valueDataList = orderServiceAdapter.getCarriers();
        return ResponseEntity.ok(valueDataList);
    }

    @GetMapping("/invoiceTypes")
    public ResponseEntity getInvoiceTypes() {
        List<DictValueData> valueDataList = orderServiceAdapter.getInvoiceTypes();
        return ResponseEntity.ok(valueDataList);
    }


    @GetMapping("/skuSpecs")
    public ResponseEntity getSkuSpecs() {
        List<DictValueData> valueDataList = orderServiceAdapter.getSkuSpecs();
        return ResponseEntity.ok(valueDataList);
    }

    @GetMapping("/paymentTypes")
    public ResponseEntity getPaymentTypes() {
        List<DictValueData> valueDataList = orderServiceAdapter.getPaymentTypes();
        return ResponseEntity.ok(valueDataList);
    }

    @GetMapping("/consignmentstatus")
    public ResponseEntity getConsignmentStatus() {
        List<DictValueData> valueDataList = orderServiceAdapter.getConsignmentStatus();
        return ResponseEntity.ok(valueDataList);
    }

}
