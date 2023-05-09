package com.sias.admin.domain;

import com.sias.commons.base.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName class
 */
@Data
public class Class extends BaseEntity implements Serializable {


    /**
     * 班级名称
     */
    private String className;

    private Integer count;
}