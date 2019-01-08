package com.mbb.stock.biz.model;

import com.mbb.common.model.BaseModel;
import com.mbb.stock.biz.enumation.PointOfServiceType;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "pointofservice")
public class PointOfServiceModel extends BaseModel {

    private String code;
    private String name;
    private Long addressId;
    private String owner;
    private String contact;
    private PointOfServiceType type;

}
