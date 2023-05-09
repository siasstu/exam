package com.sias.admin.vm;

import lombok.Data;

import java.util.List;

@Data
public class ExamPaperSubmitVM {

    private Integer id;

    private Integer doTime;

    private List<ExamPaperSubmitItemVM> answerList;

    private Integer examGradeId;

}