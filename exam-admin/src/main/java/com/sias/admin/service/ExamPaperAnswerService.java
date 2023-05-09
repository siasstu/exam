package com.sias.admin.service;

import com.github.pagehelper.PageInfo;
import com.sias.admin.vm.*;
import com.sias.commons.base.BasePage;
import com.sias.commons.base.RestResponse;
import com.sias.commons.model.SysUser;
import com.sias.commons.service.BaseService;
import com.sias.admin.domain.ExamPaperAnswer;

import java.util.List;

/**
* @author 123
* @description 针对表【exam_paper_answer】的数据库操作Service
* @createDate 2023-03-31 15:38:17
*/
public interface ExamPaperAnswerService extends BaseService<ExamPaperAnswer> {

  void calculateExamPaperAnswer(ExamPaperSubmitVM examPaperSubmitVM, SysUser user);

  PageInfo<ExamPaperAnswerPageResponseVM> scoreToVM(BasePage basePage, String idcard);



  PageInfo<ExamPaperAnswer> adminPage(ExamPaperAnswerPageRequestVM model);

  void answerCorrect(ExamPaperCorrectRequestVM requestVM);


  RestResponse toExamPaperAnswerVM(ExamPaperAnswerPageRequestVM requestVM);

  List<ExamPaperResponseItemVM> answerList(Integer id);



}
