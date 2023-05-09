package com.sias.admin.vm;

import com.sias.admin.vm.question.QuestionVM;
import lombok.Data;

@Data
public class ExamPaperResponseItemVM extends ExamPaperSubmitItemVM {
    private String correct;

    private Integer score;

    private Integer questionScore;

    private Integer doRight;

    private QuestionVM questionVM;
}