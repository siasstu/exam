package com.sias.admin.vm.question;

import com.sias.commons.base.BasePage;
import lombok.Data;

@Data
public class QuestionPageRequestVM extends BasePage {
    private Integer id;
    private Integer courseId;
    private Integer questionType;
}