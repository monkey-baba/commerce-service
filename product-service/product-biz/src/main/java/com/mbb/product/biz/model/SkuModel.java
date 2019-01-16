package com.mbb.product.biz.model;

import com.mbb.common.model.BaseModel;
import java.util.Map;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "sku")
public class SkuModel extends BaseModel {

    private String code;
    private Long productId;
    private Map<Long,String> meta;


}
