package com.sias.admin.service;

import com.github.pagehelper.PageInfo;
import com.sias.admin.domain.QuestionError;
import com.sias.admin.vm.ErrorQuestionVM;
import com.sias.admin.vm.question.QuestionPageRequestVM;
import com.sias.commons.model.SysUser;
import com.sias.commons.service.BaseService;

/**
* @author 123
* @description 针对表【question_error】的数据库操作Service
* @createDate 2023-03-31 15:59:53
*/
public interface QuestionErrorService extends BaseService<QuestionError> {

  PageInfo<ErrorQuestionVM> errorQuestionForVM(SysUser currentUser, QuestionPageRequestVM requestVM);
}
