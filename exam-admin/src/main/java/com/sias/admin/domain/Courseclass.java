package com.sias.admin.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName courseclass
 */
@Data
public class Courseclass implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 课程班级名称
     */
    private String className;

    /**
     * 教师
     */
    private String createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 逻辑删除
     */
    private Boolean deleted;

    /**
     * 课程id
     */
    private Integer subjectId;
    private String subjectName;
    private Integer classCount;

    private static final long serialVersionUID = 1L;
}