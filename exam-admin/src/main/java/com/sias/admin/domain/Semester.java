package com.sias.admin.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName semester
 */
@Data
public class Semester implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 学期名称
     */
    private String name;

    /**
     * 
     */
    private Boolean deleted;

    private static final long serialVersionUID = 1L;
}