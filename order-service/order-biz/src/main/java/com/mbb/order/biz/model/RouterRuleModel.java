package com.mbb.order.biz.model;

import com.mbb.common.model.BaseModel;
import com.mbb.order.biz.enumation.RouterRuleType;
import java.util.Date;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "router_rule")
public class RouterRuleModel  extends BaseModel {

    private String code;
    private String name;
    private Boolean enabled;
    private Integer priority;
    private RouterRuleType type;
    private Long modifyUserId;
    private Date modifyDate;
}
