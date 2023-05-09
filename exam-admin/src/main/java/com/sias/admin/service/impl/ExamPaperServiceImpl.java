package com.sias.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sias.admin.domain.*;
import com.sias.admin.domain.enums.ActionEnum;
import com.sias.admin.domain.enums.QuestionTypeEnum;
import com.sias.admin.domain.exam.ExamPaperQuestionItemObject;
import com.sias.admin.domain.exam.ExamPaperTitleItemObject;
import com.sias.admin.mapper.*;
import com.sias.admin.service.ExamPaperService;
import com.sias.admin.service.QuestionService;
import com.sias.admin.service.TextContentService;
import com.sias.admin.vm.AutoCreatePaperRequestVM;
import com.sias.admin.vm.paper.*;
import com.sias.admin.vm.question.QuestionEditRequestVM;
import com.sias.commons.base.RestResponse;
import com.sias.commons.model.SysUser;
import com.sias.commons.service.impl.BaseServiceImpl;
import com.sias.commons.utils.DateTimeUtil;
import com.sias.commons.utils.PageInfoHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author 123
 * @description 针对表【exam_paper】的数据库操作Service实现
 * @createDate 2023-03-31 14:01:52
 */
@Service
public class ExamPaperServiceImpl extends BaseServiceImpl<ExamPaper>
        implements ExamPaperService {
  private final ExamPaperMapper examPaperMapper;

  public ExamPaperServiceImpl(ExamPaperMapper examPaperMapper) {
    super(examPaperMapper);
    this.examPaperMapper = examPaperMapper;
  }

  @Resource
  TextContentService textContentService;

  @Resource
  QuestionService questionService;

  @Resource
  QuestionMapper questionMapper;

  @Resource
  ExamGradeMapper examGradeMapper;

  @Resource
  SubjectMapper subjectMapper;
  @Resource
  ExamPaperAnswerMapper examPaperAnswerMapper;

  @Resource
  CourseclassStuMapper courseclassStuMapper;

  @Override
  public PageInfo<ExamResponseVM> toExamVM(ExamPaperPageRequestVM model) {
    PageInfo<ExamPaper> pageInfo = page(model);
    return PageInfoHelper.copyMap(pageInfo, e -> {
      ExamResponseVM vm = modelMapper.map(e, ExamResponseVM.class);
      vm.setCreateTime(DateTimeUtil.dateFormat(e.getCreateTime()));
      return vm;
    });
  }

  @Override
  public PageInfo<ExamPaper> page(ExamPaperPageRequestVM requestVM) {
    PageHelper.startPage(requestVM.getPageIndex(), requestVM.getPageSize());
    return new PageInfo<>(examPaperMapper.page(requestVM));
  }

  @Override
  public List<ExamPaper> selectPaper() {
    return examPaperMapper.selectPaper();
  }

  @Override
  public ExamPaper savePaperFromVM(ExamPaperEditRequestVM model, String username) {
    ActionEnum actionEnum = (model.getId() == null) ? ActionEnum.ADD : ActionEnum.UPDATE;
    Date now = new Date();
    List<ExamPaperTitleItemVM> titleItemsVM = model.getTitleItems();
    List<ExamPaperTitleItemObject> frameTextContentList = frameTextContentFromVM(titleItemsVM);
    String frameTextContentStr = JSONUtil.toJsonStr(frameTextContentList);
    ExamPaper examPaper;
    if (actionEnum == ActionEnum.ADD) {
      examPaper = modelMapper.map(model, ExamPaper.class);
      TextContent frameTextContent = new TextContent(frameTextContentStr, now);
      textContentService.insertByFilter(frameTextContent);
      examPaper.setFrameTextContentId(frameTextContent.getId());
      examPaper.setCreateTime(now);
      examPaper.setCreateUser(username);
      examPaper.setDeleted(false);
      examPaperFromVM(examPaper, titleItemsVM);
      examPaperMapper.insertSelective(examPaper);
    } else {
      examPaper = examPaperMapper.selectByPrimaryKey(model.getId());
      TextContent frameTextContent = textContentService.selectById(examPaper.getFrameTextContentId());
      frameTextContent.setContent(frameTextContentStr);
      textContentService.updateByIdFilter(frameTextContent);
      modelMapper.map(model, examPaper);
      examPaperFromVM(examPaper, titleItemsVM);
      examPaperMapper.updateByPrimaryKeySelective(examPaper);
    }
    return examPaper;
  }

  private List<ExamPaperTitleItemObject> frameTextContentFromVM(List<ExamPaperTitleItemVM> titleItems) {
    AtomicInteger index = new AtomicInteger(1);
    return titleItems.stream().map(t -> {
      ExamPaperTitleItemObject titleItem = modelMapper.map(t, ExamPaperTitleItemObject.class);
      List<ExamPaperQuestionItemObject> questionItems = t.getQuestionItems().stream()
              .map(q -> {
                ExamPaperQuestionItemObject examPaperQuestionItemObject = modelMapper.map(q, ExamPaperQuestionItemObject.class);
                examPaperQuestionItemObject.setItemOrder(index.getAndIncrement());
                return examPaperQuestionItemObject;
              })
              .collect(Collectors.toList());
      titleItem.setQuestionItems(questionItems);
      return titleItem;
    }).collect(Collectors.toList());
  }

  private void examPaperFromVM(ExamPaper examPaper, List<ExamPaperTitleItemVM> titleItemsVM) {
    Integer questionCount = titleItemsVM.stream()
            .mapToInt(t -> t.getQuestionItems().size()).sum();
    Integer score = 0;
    for (ExamPaperTitleItemVM examPaperTitleItemVM : titleItemsVM) {
      Integer questionScore = examPaperTitleItemVM.getScore();
      int size = examPaperTitleItemVM.getQuestionItems().size();
      score += size * questionScore;
    }
    examPaper.setQuestionCount(questionCount);
    examPaper.setScore(score);
  }

  @Override
  public ExamPaperEditRequestVM examPaperToVM(Integer id) {
    ExamPaper examPaper = examPaperMapper.selectByPrimaryKey(id);
    ExamPaperEditRequestVM vm = modelMapper.map(examPaper, ExamPaperEditRequestVM.class);
    TextContent frameTextContent = textContentService.selectById(examPaper.getFrameTextContentId());
    List<ExamPaperTitleItemObject> examPaperTitleItemObjects = JSONUtil.toList(frameTextContent.getContent(), ExamPaperTitleItemObject.class);
    List<Integer> questionIds = examPaperTitleItemObjects.stream()
            .flatMap(t -> t.getQuestionItems().stream()
                    .map(q -> q.getId()))
            .collect(Collectors.toList());
    List<Question> questions = questionMapper.selectByIds(questionIds);
    List<ExamPaperTitleItemVM> examPaperTitleItemVMS = examPaperTitleItemObjects.stream().map(t -> {
      ExamPaperTitleItemVM tTitleVM = modelMapper.map(t, ExamPaperTitleItemVM.class);
      List<QuestionEditRequestVM> questionItemsVM = t.getQuestionItems().stream().map(i -> {
        Question question = questions.stream().filter(q -> q.getId().equals(i.getId())).findFirst().orElse(null);
        QuestionEditRequestVM questionEditRequestVM = questionService.getQuestionEditRequestVM(question);
        questionEditRequestVM.setItemOrder(i.getItemOrder());
        return questionEditRequestVM;
      }).collect(Collectors.toList());
      tTitleVM.setQuestionItems(questionItemsVM);
      tTitleVM.setScore(t.getScore());
      return tTitleVM;
    }).collect(Collectors.toList());
    vm.setTitleItems(examPaperTitleItemVMS);
    vm.setScore(examPaper.getScore());
    return vm;
  }

  @Override
  public void deletePaper(Integer id) {
    ExamPaper examPaper = selectById(id);
    examPaper.setDeleted(true);
    updateByIdFilter(examPaper);
  }

  @Override
  public List<StudentExamsResponseVM> examToVM(SysUser user) {
    List<CourseclassStu> grades = courseclassStuMapper.selectByUsername(user.getUsername());
    if (grades.isEmpty()) {
      return Collections.emptyList();
    } else {
      List<Integer> gradeIds = grades.stream().map(CourseclassStu::getCourseclassId).collect(Collectors.toList());
      List<ExamGrade> exams = examGradeMapper.selecetByGradeIds(gradeIds);
      return exams.stream().map(t -> {
        StudentExamsResponseVM vm = modelMapper.map(t, StudentExamsResponseVM.class);
        vm.setCourseName(subjectMapper.selectByPrimaryKey(t.getCourseId()).getName());
        ExamPaperAnswer examPaperAnswer = examPaperAnswerMapper.selectByExamGradeIdAndIdcard(t.getId(), user.getUsername());
        if (examPaperAnswer != null) {
          vm.setId(examPaperAnswer.getId());
          vm.setStatus(examPaperAnswer.getStatus());
        } else {
          vm.setStatus(0);
        }
        return vm;
      }).collect(Collectors.toList());
    }
  }

  @Override
  public RestResponse autoCreatePaper(AutoCreatePaperRequestVM testVM, String username) {
    List<Question> questions = questionMapper.selectByQuestionCourseId(testVM.getCourseId());
    List<ExamPaperTitleItemObject> vms = new ArrayList<>();
    int score = 0;
    if (testVM.getSingleChoice()>0){
      List<Question> singleChoice = questions.stream().filter(t -> t.getQuestionType().equals(QuestionTypeEnum.SingleChoice.getCode())).collect(Collectors.toList());
      if (singleChoice.size()< testVM.getSingleChoice()){
        return RestResponse.fail(300,"单选题数量不足");
      }
      ExamPaperTitleItemObject vm = getQuestion(singleChoice,testVM.getSingleChoice());
      vm.setName("单选题");
      vm.setScore(testVM.getSingleChoiceScore());
      score += testVM.getSingleChoiceScore()*testVM.getSingleChoice();
      vms.add(vm);
    }
    if (testVM.getMultipleChoice()>0){
      List<Question> multipleChoice = questions.stream().filter(t -> t.getQuestionType().equals(QuestionTypeEnum.MultipleChoice.getCode())).collect(Collectors.toList());
      if (multipleChoice.size()< testVM.getMultipleChoice()){
        return RestResponse.fail(300,"多选题数量不足");
      }
      ExamPaperTitleItemObject vm = getQuestion(multipleChoice,testVM.getMultipleChoice());
      vm.setName("多选题");
      vm.setScore(testVM.getMultipleChoiceScore());
      score+=testVM.getMultipleChoice()*testVM.getMultipleChoiceScore();
      vms.add(vm);
    }
    if (testVM.getTrueFalse()>0){
      List<Question> trueFalse = questions.stream().filter(t -> t.getQuestionType().equals(QuestionTypeEnum.TrueFalse.getCode())).collect(Collectors.toList());
      if (trueFalse.size()< testVM.getTrueFalse()){
        return RestResponse.fail(300,"判断题数量不足");
      }
      ExamPaperTitleItemObject vm = getQuestion(trueFalse,testVM.getTrueFalse());
      vm.setName("判断题");
      vm.setScore(testVM.getTrueFalseScore());
      score+=testVM.getTrueFalse()*testVM.getTrueFalseScore();
      vms.add(vm);
    }
    if (testVM.getGapFilling()>0){
      List<Question> gapFilling = questions.stream().filter(t -> t.getQuestionType().equals(QuestionTypeEnum.GapFilling.getCode())).collect(Collectors.toList());
      if (gapFilling.size()< testVM.getGapFilling()){
        return RestResponse.fail(300,"填空题数量不足");
      }
      ExamPaperTitleItemObject vm = getQuestion(gapFilling,testVM.getGapFilling());
      vm.setName("填空题");
      vm.setScore(testVM.getGapFillingScore());
      score+=testVM.getGapFilling()*testVM.getGapFillingScore();
      vms.add(vm);
    }
    if (testVM.getShortAnswer()>0){
      List<Question> shortAnswer = questions.stream().filter(t -> t.getQuestionType().equals(QuestionTypeEnum.ShortAnswer.getCode())).collect(Collectors.toList());
      if (shortAnswer.size()< testVM.getShortAnswer()){
        return RestResponse.fail(300,"简答题数量不足");
      }
      ExamPaperTitleItemObject vm = getQuestion(shortAnswer,testVM.getShortAnswer());
      vm.setName("简答题");
      vm.setScore(testVM.getShortAnswerScore());
      score+=testVM.getShortAnswer()*testVM.getShortAnswerScore();
      vms.add(vm);
    }
    String s = JSONUtil.toJsonStr(vms);
    TextContent textContent = new TextContent();
    textContent.setCreateTime(new Date());
    textContent.setContent(s);
    textContentService.insertByFilter(textContent);

    ExamPaper examPaper = new ExamPaper();
    examPaper.setName(testVM.getPaperName());
    examPaper.setCreateUser(username);
    examPaper.setScore(score);
    examPaper.setCourseId(testVM.getCourseId());
    examPaper.setCreateTime(new Date());
    examPaper.setSuggestTime(testVM.getTime());
    examPaper.setQuestionCount(testVM.getSingleChoice()+testVM.getMultipleChoice()+testVM.getTrueFalse()+testVM.getShortAnswer()+testVM.getGapFilling());
    examPaper.setFrameTextContentId(textContent.getId());
    examPaperMapper.insertSelective(examPaper);
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
