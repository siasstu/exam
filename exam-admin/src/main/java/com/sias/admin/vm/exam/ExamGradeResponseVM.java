package com.sias.admin.vm.exam;

import lombok.Data;

@Data
public class ExamGradeResponseVM {
  private String name;
  private Integer classNumber;
  private String title;
  private String courseName;
  private Integer correctingNumber;
  private Integer finishedNumber;
  private String examTime;
  private Integer examGradeId;
  private Integer gradeId;
}