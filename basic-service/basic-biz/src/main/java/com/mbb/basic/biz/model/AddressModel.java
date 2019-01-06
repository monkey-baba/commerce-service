package com.mbb.basic.biz.model;

import com.mbb.common.model.BaseModel;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "address")
public class AddressModel extends BaseModel {

    private String province;
    private String city;
    private String district;
    private String address;
    private String phone;

}
