package com.mbb.order.biz.model;

import com.mbb.common.model.BaseModel;
import com.mbb.order.biz.enumation.ConsignmentStatus;
import com.mbb.order.biz.enumation.DeliveryType;
import com.mbb.order.biz.enumation.OrderPlatform;
import com.mbb.order.biz.enumation.OrderSource;
import com.mbb.order.biz.enumation.OrderStauts;
import com.mbb.order.biz.enumation.OrderType;
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
    private ConsignmentStatus status;


}
