package com.sias.admin.vm;

import lombok.Data;

@Data
public class TestVM {
  private Integer courseId;
  private Integer singleChoice;
  private Integer multipleChoice;
  private Integer trueFalse;
  private Integer gapFilling;
  private Integer shortAnswer;
}