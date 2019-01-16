package com.mbb.enumeration.biz.model;

import com.mbb.common.model.BaseModel;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "enumeration")
public class EnumerationModel extends BaseModel {

    private String code;
    private String name;
    private String phone;
    private String email;
    private Long statusId;

}
