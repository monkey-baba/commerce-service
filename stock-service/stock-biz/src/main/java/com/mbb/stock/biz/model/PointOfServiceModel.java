package com.mbb.stock.biz.model;

import com.mbb.common.model.BaseModel;
import com.mbb.stock.common.enumation.PosType;
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
    private PosType posType;
    private Long statusId;
    private Long classifyId;

}
