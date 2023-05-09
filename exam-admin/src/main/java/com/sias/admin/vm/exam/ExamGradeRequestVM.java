package com.sias.admin.vm.exam;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ExamGradeRequestVM {

    private Integer id;

    private Integer paperId;

    private Integer time;

    private List<String> limitDateTime;

    private String title;

    private Integer gradeId;

    private Integer courseId;

    private Date createTime;
}