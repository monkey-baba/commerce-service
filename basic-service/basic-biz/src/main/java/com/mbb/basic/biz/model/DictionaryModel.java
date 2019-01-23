package com.mbb.basic.biz.model;

import com.mbb.common.model.BaseModel;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "dictionary")
public class DictionaryModel extends BaseModel {

    private String code;
    private String name;
    private Boolean editable;
}
