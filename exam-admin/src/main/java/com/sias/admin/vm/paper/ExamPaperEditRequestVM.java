package com.sias.admin.vm.paper;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ExamPaperEditRequestVM {

    private Integer id;

    private Integer courseId;

    private String name;

    private Integer suggestTime;

    private List<String> limitDateTime;

    private List<ExamPaperTitleItemVM> titleItems;

    private Integer score;

    private Date createTime;
}