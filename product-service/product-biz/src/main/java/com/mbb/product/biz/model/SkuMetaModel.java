package com.mbb.product.biz.model;

import com.mbb.common.model.BaseModel;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 可以用来查Product下有哪些颜色或者尺码用来展示
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name= "sku_meta")
public class SkuMetaModel extends BaseModel {

    /**
     * 规格
     */
    private Long specId;
    /**
     * 规则下的选项值 例如 蓝色
     */
    private String name;

}
