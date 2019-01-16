package com.mbb.customer.rest.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mbb.customer.biz.model.CustomerModel;
import com.mbb.customer.biz.service.CustomerService;
import com.mbb.customer.rest.dto.CustomerCreateData;
import com.mbb.customer.rest.dto.CustomerInfoResp;
import com.mbb.customer.rest.dto.CustomerListQuery;
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

    @GetMapping("/info")
    public ResponseEntity getCustomers(@RequestBody CustomerListQuery customerListQuery) {
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
        List<CustomerInfoResp> customerInfoRespList = dealResult(origin);
        //用data生成新的分页数据
        PageInfo<CustomerInfoResp> result = PageInfo.of(customerInfoRespList);
        result.setTotal(origin.getTotal());
        return ResponseEntity.ok(result);
    }


    @PostMapping("/create")
    public ResponseEntity createStock(@RequestBody CustomerCreateData customerCreateData) {
        CustomerModel customerModel = new CustomerModel();
        customerModel.setCode(customerCreateData.getCode());
        customerModel.setName(customerCreateData.getName());
        customerModel.setPhone(customerCreateData.getPhone());
        customerModel.setEmail(customerCreateData.getEmail());
        customerModel.setStatusId(customerCreateData.getStatusId());
        customerModel.setVersion(0);
        customerService.createStock(customerModel);
        CustomerInfoResp customerInfoResp = new CustomerInfoResp();
        customerInfoResp.setId(customerModel.getId());
        customerInfoResp.setCode(customerModel.getCode());
        customerInfoResp.setName(customerModel.getName());
        customerInfoResp.setPhone(customerModel.getPhone());
        customerInfoResp.setEmail(customerModel.getEmail());
        customerInfoResp.setStatusId(customerModel.getStatusId());
        return ResponseEntity.ok(customerInfoResp);
    }

    @GetMapping("/delete")
    public ResponseEntity deleteCustomer(@RequestParam(value = "id", required = true) String id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok(Boolean.TRUE);
    }

    private List<CustomerInfoResp> dealResult(PageInfo<CustomerModel> customers) {
        List<CustomerInfoResp> customerInfoRespList = customers.getList().stream().map(customerModel -> {
            CustomerInfoResp customerInfoResp = new CustomerInfoResp();
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
            return customerInfoResp;
        }).collect(Collectors.toList());
        return customerInfoRespList;
    }
}
