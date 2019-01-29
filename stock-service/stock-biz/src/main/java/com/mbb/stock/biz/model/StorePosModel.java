package com.mbb.stock.biz.model;

import com.mbb.common.model.BaseModel;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(name= "store_pos")
public class StorePosModel extends BaseModel {
    private Long storeId;
    private Long posId;
}
