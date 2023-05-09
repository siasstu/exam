package com.sias.admin.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName exam_grade
 */
@Data
public class ExamGrade implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 考试名称
     */
    private String title;

    /**
     * 考试班级id
     */
    private Integer gradeId;

    /**
     * 课程id
     */
    private Integer courseId;

    /**
     * 试卷id
     */
    private Integer paperId;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 考试时长
     */
    private Integer time;

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