package com.sias.admin.vm.exam;

import com.sias.commons.base.BasePage;
import lombok.Data;

import java.util.List;

@Data
public class ExamGradePageRequestVM extends BasePage {
    private Integer gradeId;
    private Integer courseId;
    private String username;
    private List<Integer> ids;
}