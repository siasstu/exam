package com.sias.admin.service.impl;

import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sias.admin.domain.Question;
import com.sias.admin.domain.QuestionError;
import com.sias.admin.domain.TextContent;
import com.sias.admin.mapper.QuestionErrorMapper;
import com.sias.admin.mapper.QuestionMapper;
import com.sias.admin.mapper.TextContentMapper;
import com.sias.admin.service.QuestionErrorService;
import com.sias.admin.vm.ErrorQuestionVM;
import com.sias.admin.vm.question.QuestionPageRequestVM;
import com.sias.admin.vm.question.QuestionVM;
import com.sias.commons.model.SysUser;
import com.sias.commons.service.impl.BaseServiceImpl;
import com.sias.commons.utils.PageInfoHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;

/**
* @author 123
* @description 针对表【question_error】的数据库操作Service实现
* @createDate 2023-03-31 15:59:53
*/
@Service
public class QuestionErrorServiceImpl extends BaseServiceImpl<QuestionError>
implements QuestionErrorService {
  private final QuestionErrorMapper questionErrorMapper;

  public QuestionErrorServiceImpl(QuestionErrorMapper questionErrorMapper) {
    super(questionErrorMapper);
    this.questionErrorMapper = questionErrorMapper;
  }

  @Resource
  QuestionMapper questionMapper;

  @Resource
  TextContentMapper textContentMapper;

  @Override
  public PageInfo<ErrorQuestionVM> errorQuestionForVM(SysUser currentUser, QuestionPageRequestVM requestVM) {
    PageHelper.startPage(requestVM.getPageIndex(),requestVM.getPageSize());
    PageInfo<QuestionError> pageInfo = new PageInfo<>(questionErrorMapper.page(currentUser.getUsername(), requestVM.getCourseId()));
    return PageInfoHelper.copyMap(pageInfo, q -> {
      ErrorQuestionVM vm = modelMapper.map(q, ErrorQuestionVM.class);
      Question question = questionMapper.selectByPrimaryKey(q.getQuestionId());
      Integer infoTextContentId = question.getInfoTextContentId();
      TextContent textContent = textContentMapper.selectByPrimaryKey(infoTextContentId);
      QuestionVM questionObject = JSONUtil.toBean(textContent.getContent(), QuestionVM.class);
      if (question.getQuestionType()==2){
        vm.setAnswerList(Arrays.asList(q.getAnswer().split(",")));
      }
      vm.setCorrect(question.getCorrect());
      vm.setQuestionVM(questionObject);
      vm.setQuestionType(question.getQuestionType());
      vm.setCourseId(question.getCourseId());
      return vm;
    });
  }
}
