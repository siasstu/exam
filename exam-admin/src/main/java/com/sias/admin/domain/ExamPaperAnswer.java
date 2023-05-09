package com.sias.admin.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName exam_paper_answer
 */
@Data
public class ExamPaperAnswer implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 所属试卷id
     */
    private Integer paperId;

    /**
     * 试卷名称
     */
    private String paperName;

    /**
     * 所属课程
     */
    private Integer courseId;

    /**
     * 班级id
     */
    private Integer gradeId;

    /**
     * 学生得分
     */
    private Integer userScore;

    /**
     * 试卷总分
     */
    private Integer paperScore;

    /**
     * 做对题目数量
     */
    private Integer questionCorrect;

    /**
     * 题目数量
     */
    private Integer questionCount;

    /**
     * 做题时间
     */
    private Integer doTime;

    /**
     * 试卷状态
     */
    private Integer status;

    /**
     * 创建人id
     */
    private String userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 答题详情
     */
    private Integer textContentId;

    /**
     * 考试详情id
     */
    private Integer examGradeId;

    /**
     * 是否删除
     */
    private Boolean deleted;

    private static final long serialVersionUID = 1L;
}