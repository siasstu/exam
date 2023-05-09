package com.sias.admin.service.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sias.admin.domain.Question;
import com.sias.admin.domain.TextContent;
import com.sias.admin.domain.enums.QuestionTypeEnum;
import com.sias.admin.mapper.QuestionMapper;
import com.sias.admin.service.QuestionService;
import com.sias.admin.service.TextContentService;
import com.sias.admin.vm.question.*;
import com.sias.commons.service.impl.BaseServiceImpl;
import com.sias.commons.utils.DateTimeUtil;
import com.sias.commons.utils.ExamUtil;
import com.sias.commons.utils.PageInfoHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 123
 * @description 针对表【question】的数据库操作Service实现
 * @createDate 2023-03-31 12:57:41
 */
@Service
public class QuestionServiceImpl extends BaseServiceImpl<Question>
        implements QuestionService {

  private final QuestionMapper questionMapper;

  public QuestionServiceImpl(QuestionMapper questionMapper) {
    super(questionMapper);
    this.questionMapper = questionMapper;
  }

  @Resource
  TextContentService textContentService;

  @Override
  public void insertFullQuestion(QuestionEditRequestVM model, String username) {
    Date now = new Date();
    TextContent infoTextContent = new TextContent();
    infoTextContent.setCreateTime(now);
    setQuestionInfoFromVM(infoTextContent, model);
    textContentService.insertByFilter(infoTextContent);
    Question question = new Question();
    question.setQuestionType(model.getQuestionType());
    question.setCourseId(model.getCourseId());
    question.setCorrectFromVM(model.getCorrect(), model.getCorrectArray());
    question.setInfoTextContentId(infoTextContent.getId());
    question.setCreateUser(username);
    question.setCreateTime(now);
    questionMapper.insertSelective(question);
  }

  public void setQuestionInfoFromVM(TextContent infoTextContent, QuestionEditRequestVM model) {
    List<QuestionEditItemVM> itemObjects = model.getItems().stream().map(i ->
            {
              QuestionEditItemVM item = new QuestionEditItemVM();
              item.setPrefix(i.getPrefix());
              item.setContent(i.getContent());
              return item;
            }
    ).collect(Collectors.toList());
    QuestionVM vm = new QuestionVM();
    vm.setQuestionEditItemVMS(itemObjects);
    vm.setTitleContent(model.getTitle());
    vm.setCorrect(model.getCorrect());
    infoTextContent.setContent(JSONUtil.toJsonStr(vm));
  }

  @Override
  public void updateFullQuestion(QuestionEditRequestVM model) {
    Question question = questionMapper.selectByPrimaryKey(model.getId());
    question.setCorrectFromVM(model.getCorrect(), model.getCorrectArray());
    questionMapper.updateByPrimaryKeySelective(question);
    TextContent infoTextContent = textContentService.selectById(question.getInfoTextContentId());
    setQuestionInfoFromVM(infoTextContent, model);
    textContentService.updateByIdFilter(infoTextContent);
  }

  @Override
  public PageInfo<QuestionResponseVM> page(QuestionPageRequestVM requestVM) {
    PageHelper.startPage(requestVM.getPageIndex(), requestVM.getPageSize());
    PageInfo<Question> pageInfo = new PageInfo<>(questionMapper.page(requestVM));
    return PageInfoHelper.copyMap(pageInfo, q -> {
      QuestionResponseVM vm = modelMapper.map(q, QuestionResponseVM.class);
      vm.setCreateTime(DateTimeUtil.dateFormat(q.getCreateTime()));
      TextContent textContent = textContentService.selectById(q.getInfoTextContentId());
      JSONObject jsonObject = JSONUtil.parseObj(textContent.getContent());
      vm.setShortTitle((String) jsonObject.get("titleContent"));
      return vm;
    });
  }

  @Override
  public QuestionEditRequestVM getQuestionEditRequestVM(Integer id) {
    Question question = questionMapper.selectByPrimaryKey(id);
    return getQuestionEditRequestVM(question);
  }

  @Override
  public QuestionEditRequestVM getQuestionEditRequestVM(Question question) {
    TextContent textContent = textContentService.selectById(question.getInfoTextContentId());
    QuestionVM vm = JSONUtil.toBean(textContent.getContent(), QuestionVM.class);
    QuestionEditRequestVM questionEditRequestVM = modelMapper.map(question, QuestionEditRequestVM.class);
    questionEditRequestVM.setTitle(vm.getTitleContent());

    QuestionTypeEnum questionTypeEnum = QuestionTypeEnum.fromCode(question.getQuestionType());
    switch (questionTypeEnum) {
      case SingleChoice:
      case TrueFalse:
        questionEditRequestVM.setCorrect(question.getCorrect());
        break;
      case MultipleChoice:
        questionEditRequestVM.setCorrectArray(ExamUtil.contentToArray(question.getCorrect()));
        break;
      case GapFilling:
        List<String> correctContent = vm.getQuestionEditItemVMS().stream().map(QuestionEditItemVM::getContent).collect(Collectors.toList());
        questionEditRequestVM.setCorrectArray(correctContent);
        break;
      case ShortAnswer:
        questionEditRequestVM.setCorrect(vm.getCorrect());
        break;
      default:
        break;
    }
    List<QuestionEditItemVM> editItems = vm.getQuestionEditItemVMS().stream().map(o -> {
      return modelMapper.map(o, QuestionEditItemVM.class);
    }).collect(Collectors.toList());
    questionEditRequestVM.setItems(editItems);
    return questionEditRequestVM;
  }

}
