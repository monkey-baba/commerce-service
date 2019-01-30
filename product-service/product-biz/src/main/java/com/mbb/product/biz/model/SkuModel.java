package com.mbb.product.biz.model;

import com.mbb.common.handler.ListTypeHandler;
import com.mbb.common.handler.MapTypeHandler;
import com.mbb.common.model.BaseModel;

import java.util.List;
import java.util.Map;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import tk.mybatis.mapper.annotation.ColumnType;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "sku")
public class SkuModel extends BaseModel {

    private String code;
    private String name;
    private Long productId;
    /**
     * 规格对应的值
     */
    @ColumnType(typeHandler = ListTypeHandler.class)
    private List<Map<Long,Long>> meta;


}
