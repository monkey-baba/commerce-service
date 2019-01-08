package com.mbb.stock.biz.model;

import com.mbb.common.model.BaseModel;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "warehouse")
public class WarehouseModel extends BaseModel {

    private String code;
    private String name;
    private Boolean active;
    private Long posId;

}
