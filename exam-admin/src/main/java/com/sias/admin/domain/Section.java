package com.sias.admin.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @TableName section
 */
@Data
public class Section implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 章节名称
     */
    private String name;

    /**
     * 所属课程
     */
    private Integer subjectId;

    /**
     * 介绍
     */
    private String remark;

    private List<Knows> children = new ArrayList<>();

    private static final long serialVersionUID = 1L;
}