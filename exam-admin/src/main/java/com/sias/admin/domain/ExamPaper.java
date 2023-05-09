package com.sias.admin.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName exam_paper
 */
@Data
public class ExamPaper implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 试卷名称
     */
    private String name;

    /**
     * 课程id
     */
    private Integer courseId;

    /**
     * 总分
     */
    private Integer score;

    /**
     * 题目数量
     */
    private Integer questionCount;

    /**
     * 考试时长
     */
    private Integer suggestTime;

    /**
     * 题目内容
     */
    private Integer frameTextContentId;

    /**
     * 创建人id
     */
    private String createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 是否删除
     */
    private Boolean deleted;

    private static final long serialVersionUID = 1L;
}