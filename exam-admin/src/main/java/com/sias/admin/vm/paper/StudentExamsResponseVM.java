package com.sias.admin.vm.paper;

import lombok.Data;

import java.util.Date;

@Data
public class StudentExamsResponseVM {

    private Integer id;
    /**
     * 考试名称
     */
    private String title;

    /**
     * 试卷id
     */
    private Integer paperId;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 考试时长
     */
    private Integer time;

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 考试状态
     */
    private Integer status;


}