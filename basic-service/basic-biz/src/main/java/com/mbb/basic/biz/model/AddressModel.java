package com.mbb.basic.biz.model;

import com.mbb.common.model.BaseModel;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "address")
public class AddressModel extends BaseModel {

    /**
     * 联系人
     */
    private String name;
    private String province;
    private String city;
    private String district;
    private String address;
    private String phone;

}
