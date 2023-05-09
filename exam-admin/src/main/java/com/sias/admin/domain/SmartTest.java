package com.sias.admin.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName smart_test
 */
@Data
public class SmartTest implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 课程id
     */
    private Integer courseId;

    /**
     * 班级id
     */
    private String name;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 是否删除
     */
    private Boolean deleted;

    /**
     * 
     */
    private Integer textContentId;

    private static final long serialVersionUID = 1L;
}