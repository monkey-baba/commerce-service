package com.mbb.order.biz.model;

import com.mbb.common.model.BaseModel;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "consignment_entry")
public class ConsignmentEntryModel extends BaseModel {

    private Long skuId;
    private Long orderEntryId;
    private Long shippedQuantity;
    private Long consignmentId;
}
