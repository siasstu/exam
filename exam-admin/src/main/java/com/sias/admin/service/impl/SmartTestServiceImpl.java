package com.sias.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.sias.admin.domain.Question;
import com.sias.admin.domain.TextContent;
import com.sias.admin.domain.enums.QuestionTypeEnum;
import com.sias.admin.domain.exam.ExamPaperQuestionItemObject;
import com.sias.admin.domain.exam.ExamPaperTitleItemObject;
import com.sias.admin.mapper.QuestionMapper;
import com.sias.admin.service.QuestionService;
import com.sias.admin.service.TextContentService;
import com.sias.admin.vm.TestVM;
import com.sias.admin.vm.paper.ExamPaperEditRequestVM;
import com.sias.admin.vm.paper.ExamPaperTitleItemVM;
import com.sias.admin.vm.question.QuestionEditRequestVM;
import com.sias.commons.base.BasePage;
import com.sias.commons.base.RestResponse;
import com.sias.commons.service.impl.BaseServiceImpl;
import com.sias.admin.domain.SmartTest;
import com.sias.admin.mapper.SmartTestMapper;
import com.sias.admin.service.SmartTestService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author 123
* @description 针对表【smart_test】的数据库操作Service实现
* @createDate 2023-04-01 17:21:35
*/
@Service
public class SmartTestServiceImpl extends BaseServiceImpl<SmartTest>
implements SmartTestService{
  private final SmartTestMapper smartTestMapper;

  public SmartTestServiceImpl(SmartTestMapper smartTestMapper) {
    super(smartTestMapper);
    this.smartTestMapper=smartTestMapper;
  }
  @Resource
  QuestionMapper questionMapper;

  @Resource
  TextContentService textContentService;

  @Resource
  QuestionService questionService;

  @Override
  public ExamPaperEditRequestVM testToVM(Integer id) {
    SmartTest examPaper = smartTestMapper.selectByPrimaryKey(id);
    ExamPaperEditRequestVM vm = modelMapper.map(examPaper, ExamPaperEditRequestVM.class);
    TextContent frameTextContent = textContentService.selectById(examPaper.getTextContentId());
    List<ExamPaperTitleItemObject> examPaperTitleItemObjects = JSONUtil.toList(frameTextContent.getContent(), ExamPaperTitleItemObject.class);
    List<Integer> questionIds = examPaperTitleItemObjects.stream()
            .flatMap(t -> t.getQuestionItems().stream()
                    .map(ExamPaperQuestionItemObject::getId))
            .collect(Collectors.toList());
    List<Question> questions = questionMapper.selectByIds(questionIds);
    List<ExamPaperTitleItemVM> examPaperTitleItemVms = examPaperTitleItemObjects.stream().map(t -> {
      ExamPaperTitleItemVM titleItemVm = modelMapper.map(t, ExamPaperTitleItemVM.class);
      List<QuestionEditRequestVM> questionItemsVm = t.getQuestionItems().stream().map(i -> {
        Question question = questions.stream().filter(q -> q.getId().equals(i.getId())).findFirst().get();
        return questionService.getQuestionEditRequestVM(question);
      }).collect(Collectors.toList());
      titleItemVm.setQuestionItems(questionItemsVm);
      return titleItemVm;
    }).collect(Collectors.toList());
    vm.setTitleItems(examPaperTitleItemVms);
    return vm;
  }

  @Override
  public PageInfo page(BasePage basePage) {
    PageMethod.startPage(basePage.getPageIndex(),basePage.getPageSize());
    return new PageInfo(smartTestMapper.select());
  }

  @Override
  public RestResponse createTest(TestVM testVM) {
    List<Question> questions = questionMapper.selectByQuestionCourseId(testVM.getCourseId());
    List<ExamPaperTitleItemObject> vms = new ArrayList<>();
    if (testVM.getSingleChoice()>0){
      List<Question> singleChoice = questions.stream().filter(t -> t.getQuestionType().equals(QuestionTypeEnum.SingleChoice.getCode())).collect(Collectors.toList());
      if (singleChoice.size()< testVM.getSingleChoice()){
        return RestResponse.fail(300,"单选题数量不足");
      }
      ExamPaperTitleItemObject vm = getQuestion(singleChoice,testVM.getSingleChoice());
      vm.setName("单选题");
      vms.add(vm);
    }
    if (testVM.getMultipleChoice()>0){
      List<Question> multipleChoice = questions.stream().filter(t -> t.getQuestionType().equals(QuestionTypeEnum.MultipleChoice.getCode())).collect(Collectors.toList());
      if (multipleChoice.size()< testVM.getMultipleChoice()){
        return RestResponse.fail(300,"多选题数量不足");
      }
      ExamPaperTitleItemObject vm = getQuestion(multipleChoice,testVM.getMultipleChoice());
      vm.setName("多选题");
      vms.add(vm);
    }
    if (testVM.getTrueFalse()>0){
      List<Question> trueFalse = questions.stream().filter(t -> t.getQuestionType().equals(QuestionTypeEnum.TrueFalse.getCode())).collect(Collectors.toList());
      if (trueFalse.size()< testVM.getTrueFalse()){
        return RestResponse.fail(300,"判断题数量不足");
      }
      ExamPaperTitleItemObject vm = getQuestion(trueFalse,testVM.getTrueFalse());
      vm.setName("判断题");
      vms.add(vm);
    }
    if (testVM.getGapFilling()>0){
      List<Question> gapFilling = questions.stream().filter(t -> t.getQuestionType().equals(QuestionTypeEnum.GapFilling.getCode())).collect(Collectors.toList());
      if (gapFilling.size()< testVM.getGapFilling()){
        return RestResponse.fail(300,"填空题数量不足");
      }
      ExamPaperTitleItemObject vm = getQuestion(gapFilling,testVM.getGapFilling());
      vm.setName("判断题");
      vms.add(vm);
    }
    if (testVM.getShortAnswer()>0){
      List<Question> shortAnswer = questions.stream().filter(t -> t.getQuestionType().equals(QuestionTypeEnum.ShortAnswer.getCode())).collect(Collectors.toList());
      if (shortAnswer.size()< testVM.getShortAnswer()){
        return RestResponse.fail(300,"简答题数量不足");
      }
      ExamPaperTitleItemObject vm = getQuestion(shortAnswer,testVM.getShortAnswer());
      vm.setName("判断题");
      vms.add(vm);
    }
    String s = JSONUtil.toJsonStr(vms);
    TextContent textContent = new TextContent();
    textContent.setCreateTime(new Date());
    textContent.setContent(s);
    textContentService.insertByFilter(textContent);
    SmartTest smartTest = new SmartTest();
    smartTest.setCreateTime(new Date());
    smartTest.setTextContentId(textContent.getId());
    smartTest.setCourseId(testVM.getCourseId());
    int size = smartTestMapper.select().size();
    smartTest.setName("智能测试"+(size+1));
    smartTestMapper.insertSelective(smartTest);
    return RestResponse.ok();
  }

  private List<Integer> getEleList(int sourceSize,int resultSize){
    List<Integer> list = CollUtil.newArrayList();
    for(int i=0;i<sourceSize;i++){
      list.add(i);
    }
    return RandomUtil.randomEleList(list,resultSize);
  }

  public ExamPaperTitleItemObject getQuestion(List<Question> questions,Integer size){
    List<Integer> list = getEleList(questions.size(), size);
    ExamPaperTitleItemObject vm = new ExamPaperTitleItemObject();
    List<ExamPaperQuestionItemObject> questionsId = new ArrayList<>();
    for (Integer index : list) {
      Question question = questions.get(index);
      ExamPaperQuestionItemObject questionItem = new ExamPaperQuestionItemObject();
      questionItem.setId(question.getId());
      questionsId.add(questionItem);
    }
    vm.setQuestionItems(questionsId);
    return vm;
  }
}
