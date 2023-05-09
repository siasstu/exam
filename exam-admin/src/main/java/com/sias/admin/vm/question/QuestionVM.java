package com.sias.admin.vm.question;

import lombok.Data;

import java.util.List;

@Data
public class QuestionVM {

    private String titleContent;

    private List<QuestionEditItemVM> questionEditItemVMS;

    private String correct;
}