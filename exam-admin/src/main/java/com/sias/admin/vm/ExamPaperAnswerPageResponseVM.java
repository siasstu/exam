package com.sias.admin.vm;

import lombok.Data;

@Data
public class ExamPaperAnswerPageResponseVM {
    private Integer id;

    private String createTime;

    private Integer userScore;

    private String courseName;

    private Integer questionCount;

    private Integer questionCorrect;

    private Integer paperScore;

    private String doTime;

    private String status;

    private String idcard;

    private String paperName;

    private String userName;

    private String realName;

    private Integer courseId;
    private Integer total;
}