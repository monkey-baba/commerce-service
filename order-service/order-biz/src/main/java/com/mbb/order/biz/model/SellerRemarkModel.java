package com.mbb.order.biz.model;

import com.mbb.common.model.BaseModel;
import java.util.Date;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "seller_remark")
public class SellerRemarkModel extends BaseModel {

    private Date date;
    private Long userId;
    private String remark;
    private Long orderId;
}
