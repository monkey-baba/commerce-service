package com.mbb.stock.biz.model;

import com.mbb.common.model.BaseModel;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "stock")
public class StockModel extends BaseModel {

    private Long skuId;
    private Long warehouseId;
    private Long available;

}
