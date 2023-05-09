package com.sias.admin.vm;

import com.sias.commons.base.BasePage;
import lombok.Data;

@Data
public class ExamPaperAnswerPageRequestVM extends BasePage {
    private Integer status;
    private Integer examGradeId;
    private Integer gradeId;
    private String idcard;
}