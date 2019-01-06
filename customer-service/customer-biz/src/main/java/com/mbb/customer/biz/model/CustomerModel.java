package com.mbb.customer.biz.model;

import com.mbb.common.model.BaseModel;
import com.mbb.customer.biz.enumation.CustomerStatus;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "customer")
public class CustomerModel extends BaseModel {

    private String code;
    private String name;
    private String phone;
    private String email;
    private CustomerStatus status;

}
