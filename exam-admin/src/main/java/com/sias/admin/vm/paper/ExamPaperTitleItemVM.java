package com.sias.admin.vm.paper;

import com.sias.admin.vm.question.QuestionEditRequestVM;

import lombok.Data;

import java.util.List;

@Data
public class ExamPaperTitleItemVM {

    private String name;

    private List<QuestionEditRequestVM> questionItems;

    private Integer score;
}