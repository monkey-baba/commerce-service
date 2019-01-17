package com.mbb.customer.rest.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lxm.idgenerator.service.intf.IdService;
import com.mbb.customer.biz.model.CustomerModel;
import com.mbb.customer.biz.service.CustomerService;
import com.mbb.customer.rest.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private IdService idService;

    @GetMapping("/info")
    public ResponseEntity getCustomers(CustomerListQuery customerListQuery) {
        CustomerModel customerModel = new CustomerModel();
        customerModel.setCode(customerListQuery.getCode());
        customerModel.setName(customerListQuery.getName());
        customerModel.setPhone(customerListQuery.getPhone());

        //开启分页
        PageHelper.startPage(customerListQuery.getPageNum(), customerListQuery.getPageSize());
        //查询数据
        List<CustomerModel> customers = customerService.getCustomers(customerModel);
        //获取页码等信息
        PageInfo<CustomerModel> origin = PageInfo.of(customers);
        //从model转data
        List<CustomerInfoResp> customerInfoRespList = origin.getList().stream().map(customer -> {
            CustomerInfoResp customerInfoResp = new CustomerInfoResp();
            convertCustomer(customer, customerInfoResp);
            return customerInfoResp;
        }).collect(Collectors.toList());
        //用data生成新的分页数据
        PageInfo<CustomerInfoResp> result = PageInfo.of(customerInfoRespList);
        result.setTotal(origin.getTotal());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/update")
    public ResponseEntity updateUser(@RequestBody CustomerUpdateData data) {
        CustomerModel customerModel = customerService.findById(data.getId());
        customerModel.setId(data.getId());
        customerModel.setCode(data.getCode());
        customerModel.setName(data.getName());
        customerModel.setPhone(data.getPhone());
        customerModel.setEmail(data.getEmail());
        customerModel.setStatusId(data.getStatusId());
        customerService.updateCustomer(customerModel);
        return ResponseEntity.ok("更新成功");
    }


    @PostMapping("/create")
    public ResponseEntity createStock(@RequestBody CustomerCreateData customerCreateData) {
        CustomerModel customerModel = new CustomerModel();
        customerModel.setId(idService.genId());
        customerModel.setCode(customerCreateData.getCode());
        customerModel.setName(customerCreateData.getName());
        customerModel.setPhone(customerCreateData.getPhone());
        customerModel.setEmail(customerCreateData.getEmail());
        customerModel.setStatusId(customerCreateData.getStatusId());
        customerModel.setVersion(0);
        customerService.createStock(customerModel);
        CustomerInfoResp customerInfoResp = new CustomerInfoResp();
        convertCustomer(customerModel, customerInfoResp);
        return ResponseEntity.ok(customerInfoResp);
    }

    @PostMapping("/delete")
    public ResponseEntity deleteCustomer(@RequestBody List<CustomerDeleteData> datas) {
        for (CustomerDeleteData data : datas) {
            customerService.deleteCustomer(data.getCode());
        }

        return ResponseEntity.ok("删除成功");
    }

    private void convertCustomer(CustomerModel customerModel, CustomerInfoResp customerInfoResp) {
        //主键
        customerInfoResp.setId(customerModel.getId());
        //客户编号
        customerInfoResp.setCode(customerModel.getCode());
        //客户名称
        customerInfoResp.setName(customerModel.getName());
        //手机号
        customerInfoResp.setPhone(customerModel.getPhone());
        //邮箱
        customerInfoResp.setEmail(customerModel.getEmail());
        //状态
        customerInfoResp.setStatusId(customerModel.getStatusId());
    }
}
