package com.mbb.product.biz.model;

import com.mbb.common.model.BaseModel;
import java.util.List;
import java.util.Map;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "product")
public class ProductModel extends BaseModel {

    private String code;
    private String name;
    private Long channelId;
    private Long unitId;
    private Long approvedId;
    private List<String> images;
    private Map<Long,String> attribute;

}
