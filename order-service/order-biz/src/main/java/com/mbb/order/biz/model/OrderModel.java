package com.mbb.order.biz.model;

import com.mbb.common.model.BaseModel;
import java.util.Date;
import java.util.List;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "`order`")
public class OrderModel extends BaseModel {

    private String ecsOrderId;
    private String code;
    private Long storeId;
    private Long customerId;
    private String receiver;
    private String receiverPhone;
    private Long posId;
    private Double totalPrice;
    private Double deliveryCost;
    private Double subTotal;
    private Double discount;
    private Date date;
    private Date paymentDate;
    private Long statusId;
    private Long orderTypeId;
    private Long addressId;
    private Long platformId;
    private Long orderSourceId;
    private Long channelId;
    private String remark;
    private Long deliveryTypeId;
    private Long carrierId;
    private Long pointPosId;


    @Transient
    private List<PaymentModel> payments;

    @Transient
    private List<OrderEntryModel> entries;

    @Transient
    private List<SellerRemarkModel> sellerRemarks;

    @Transient
    private InvoiceModel invoice;

    @Transient
    private List<ConsignmentModel> consignments;

}
