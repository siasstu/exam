package com.sias.admin.vm;

import com.sias.admin.vm.question.QuestionVM;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author 吴文杰
 * @version 1.0
 * creates 2023-03-2023 12:34
 */
@Data
public class ErrorQuestionVM   {

  private Integer id;
  private Integer questionType;

  /**
   * 题目id
   */
  private Integer questionId;

  /**
   * 创建时间
   */
  private Date createTime;

  private QuestionVM questionVM;

  /**
   * 学生答案
   */
  private String answer;
  private String correct;

  private List<String> answerList;

  /**
   * 得分
   */
  private String score;

  /**
   * 题分
   */
  private String questionScore;

  /**
   * 所属试卷
   */
  private Integer paperId;
  /**
   * 所属试卷
   */
  private Integer courseId;
  /**
   * 创建人学号
   */
  private String idcard;
}