package com.sias.admin.domain;

import java.io.Serializable;
import java.util.Date;

import com.sias.commons.base.BaseEntity;
import lombok.Data;

/**
 * 
 * @TableName subject
 */
@Data
public class Subject extends BaseEntity implements Serializable {

    /**
     * 课程名称
     */
    private String name;

    /**
     * 创建人
     */
    private String createUser;

    private Integer semesterId;

    private static final long serialVersionUID = 1L;
}