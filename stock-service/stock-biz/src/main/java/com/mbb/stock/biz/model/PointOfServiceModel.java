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

    public PosType getPos_type() {
        return pos_type;
    }

    public void setPos_type(PosType pos_type) {
        this.pos_type = pos_type;
    }

    private PosType pos_type;
    private Long statusId;
    private Long classifyId;

}
