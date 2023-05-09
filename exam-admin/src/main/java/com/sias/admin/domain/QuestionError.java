package com.sias.admin.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName question_error
 */
@Data
public class QuestionError implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 题目id
     */
    private Integer questionId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 正确答案
     */
    private String correct;

    /**
     * 学生答案
     */
    private String answer;

    /**
     * 得分
     */
    private String score;

    /**
     * 题分
     */
    private String questionScore;

    /**
     * 所属试卷
     */
    private Integer paperId;

    /**
     * 是否删除
     */
    private Boolean deleted;

    /**
     * 创建人学号
     */
    private String idcard;

    /**
     * 所属课程
     */
    private Integer courseId;

    /**
     * 所属班级考试
     */
    private Integer examGradeId;

    /**
     * 题目序号
     */
    private Integer itemOrder;

    private static final long serialVersionUID = 1L;
}