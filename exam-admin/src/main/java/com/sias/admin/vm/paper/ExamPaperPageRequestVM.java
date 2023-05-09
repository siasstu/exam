package com.sias.admin.vm.paper;

import com.sias.commons.base.BasePage;
import lombok.Data;

@Data
public class ExamPaperPageRequestVM extends BasePage {

    private Integer id;

    private Integer courseId;
}