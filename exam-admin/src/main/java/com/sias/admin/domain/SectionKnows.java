package com.sias.admin.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.sias.commons.base.BaseEntity;
import lombok.Data;

/**
 * 
 * @TableName section_knows
 */
@Data
public class SectionKnows extends BaseEntity implements Serializable {
    /**
     * 名称
     */
    private String name;

    /**
     * 
     */
    private Long parentId;

    /**
     * 状态
     */
    private Integer status;


    private Integer subjectId;

    /**
     * 排序
     */
    private Integer orderNum;

    private List<SectionKnows> children = new ArrayList<>();

    private static final long serialVersionUID = 1L;
}