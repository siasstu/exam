package com.sias.admin.service;

import com.github.pagehelper.PageInfo;
import com.sias.admin.vm.question.QuestionEditRequestVM;
import com.sias.admin.vm.question.QuestionPageRequestVM;
import com.sias.admin.vm.question.QuestionResponseVM;
import com.sias.commons.service.BaseService;
import com.sias.admin.domain.Question;

/**
* @author 123
* @description 针对表【question】的数据库操作Service
* @createDate 2023-03-31 12:57:41
*/
public interface QuestionService extends BaseService<Question> {

  void insertFullQuestion(QuestionEditRequestVM requestVM, String username);

  void updateFullQuestion(QuestionEditRequestVM requestVM);

  PageInfo<QuestionResponseVM> page(QuestionPageRequestVM model);

  QuestionEditRequestVM getQuestionEditRequestVM(Integer id);
  QuestionEditRequestVM getQuestionEditRequestVM(Question question);
}
