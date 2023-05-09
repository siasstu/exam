package com.sias.admin.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName class_stu
 */
@Data
public class ClassStu implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 班级编号
     */
    private Integer classId;

    /**
     * 学号
     */
    private String username;

    private static final long serialVersionUID = 1L;
}