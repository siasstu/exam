package com.sias.admin.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName knows
 */
@Data
public class Knows implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 知识点
     */
    private String name;

    /**
     * 所属章节
     */
    private Integer sectionId;

    /**
     * 描述
     */
    private String describe;

    /**
     * 状态
     */
    private String status;

    private static final long serialVersionUID = 1L;
}