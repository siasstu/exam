package com.sias.admin.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName courseclass_stu
 */
@Data
public class CourseclassStu implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 课程班级id
     */
    private Integer courseclassId;

    /**
     * 学生用户名
     */
    private String username;

    private static final long serialVersionUID = 1L;
}