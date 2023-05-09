package com.sias.admin.vm.question;

import lombok.Data;

@Data
public class QuestionResponseVM {

    private Integer id;

    private Integer questionType;

    private Integer textContentId;

    private String createTime;

    private Integer courseId;

    private String createUser;

    private String score;

    private Integer status;

    private String correct;

    private String shortTitle;
}