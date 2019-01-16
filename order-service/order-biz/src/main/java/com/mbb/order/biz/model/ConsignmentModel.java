package com.mbb.order.biz.model;

import com.mbb.common.model.BaseModel;
import java.util.Date;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "consignment")
public class ConsignmentModel extends BaseModel {

    private String code;
    private Long orderId;
    private Date date;
    private String expressNum;
    private Long carrierId;
    private Long posId;
    private Long statusId;


}
