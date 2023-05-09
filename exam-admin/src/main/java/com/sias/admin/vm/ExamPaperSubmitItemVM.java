package com.sias.admin.vm;

import lombok.Data;

import java.util.List;

@Data
public class ExamPaperSubmitItemVM {

    private Integer id;

    private Integer questionId;

    private String answer;

    private List<String> answerList;

    private Integer questionType;
    private Integer itemOrder;
}