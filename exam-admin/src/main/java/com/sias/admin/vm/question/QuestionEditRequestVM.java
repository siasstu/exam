package com.sias.admin.vm.question;

import lombok.Data;

import java.util.List;

@Data
public class QuestionEditRequestVM {

    private Integer id;

    private Integer questionType;

    private Integer courseId;

    private String title;

    private List<QuestionEditItemVM> items;

    private List<String> correctArray;

    private String correct;

    private Integer itemOrder;
}