package com.mbb.basic.biz.dictionaryvalue.biz.model;

import com.mbb.common.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "dictionaryValue_value")
public class DictionaryValueModel extends BaseModel {

    private String code;
    private String name;
    private String description;
    private Long typeId;
    private String attribute1;
    private String attribute2;
    private String attribute3;
    private Boolean active;
}
