package com.sias.admin.vm;

import lombok.Data;

import java.util.List;

@Data
public class ExamPaperCorrectRequestVM {
  private Integer id;
  private List<ExamPaperResponseItemVM> examPaperResponseItemVMS;
}