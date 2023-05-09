package com.sias.admin.vm.paper;

import lombok.Data;

@Data
public class ExamResponseVM {

    private Integer id;

    private String name;

    private Integer questionCount;

    private Integer score;

    private String createTime;

    private Integer createUser;

    private Integer suggestTime;

    private Integer courseId;

    private Integer frameTextContentId;

}