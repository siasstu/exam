package com.sias.admin.vm;

import lombok.Data;

/**
 * @author 吴文杰
 * @version 1.0
 * @createTime 2023-04-01 14:33:02
 */
@Data
public class AutoCreatePaperRequestVM {
  private Integer courseId;
  private String paperName;
  private Integer singleChoice;
  private Integer singleChoiceScore;
  private Integer multipleChoice;
  private Integer multipleChoiceScore;
  private Integer trueFalse;
  private Integer trueFalseScore;
  private Integer gapFilling;
  private Integer gapFillingScore;
  private Integer shortAnswer;
  private Integer shortAnswerScore;
  private Integer time;
}
