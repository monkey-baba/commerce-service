package com.mbb.product.biz.model;

import com.mbb.common.model.BaseModel;
import com.mbb.product.biz.enumation.ApprovedStatus;
import java.util.List;
import java.util.Map;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "product_category")
public class ProductCategoryRel extends BaseModel {

    private Long productId;
    private Long categoryId;

}
