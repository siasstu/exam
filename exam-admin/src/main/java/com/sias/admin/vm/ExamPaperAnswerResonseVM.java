package com.sias.admin.vm;

import lombok.Data;

import java.util.List;

@Data
public class ExamPaperAnswerResonseVM {
    private List<ExamPaperAnswerPageResponseVM> list;
    /**
     * 查询总数
     */
    private Integer total;
    /**
     * 应考试人数
     */
    private Integer examCount;
    /**
     * 考试名称
     */
    private String examName;
    /**
     * 考试时间
     */
    private String limitTime;
    /**
     * 考试时长
     */
    private Integer time;
    /**
     * 课程名称
     */
    private String courseName;
    /**
     * 待批改人数
     */
    private Integer correctCount;
    /**
     * 题目数量
     */
    private Integer questionCount;
    /**
     * 以考试人数
     */
    private Integer finishedCount;

    /**
     * 试卷总分
     */
    private Integer paperCount;
    /**
     * 考试班级名称
     */
    private String gradeName;

    private List<Integer> errorCountList;
    private List<Integer> errorQuestionIdList;
}
