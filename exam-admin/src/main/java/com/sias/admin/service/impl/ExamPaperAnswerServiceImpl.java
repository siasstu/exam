package com.sias.admin.service.impl;

import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sias.admin.domain.*;
import com.sias.admin.domain.enums.ExamPaperAnswerStatusEnum;
import com.sias.admin.domain.enums.QuestionTypeEnum;
import com.sias.admin.domain.exam.ExamPaperTitleItemObject;
import com.sias.admin.mapper.*;
import com.sias.admin.service.CourseclassStuService;
import com.sias.admin.service.ExamPaperAnswerService;
import com.sias.admin.service.TextContentService;
import com.sias.admin.vm.*;
import com.sias.admin.vm.question.QuestionVM;
import com.sias.commons.base.BasePage;
import com.sias.commons.base.RestResponse;
import com.sias.commons.model.SysUser;
import com.sias.commons.service.impl.BaseServiceImpl;
import com.sias.commons.utils.DateTimeUtil;
import com.sias.commons.utils.ExamUtil;
import com.sias.commons.utils.PageInfoHelper;
import com.sias.system.mapper.SysUserMapper;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author 123
* @description 针对表【exam_paper_answer】的数据库操作Service实现
* @createDate 2023-03-31 15:38:17
*/
@Service
public class ExamPaperAnswerServiceImpl extends BaseServiceImpl<ExamPaperAnswer>
implements ExamPaperAnswerService {
 private final ExamPaperAnswerMapper examPaperAnswerMapper;

  public ExamPaperAnswerServiceImpl(ExamPaperAnswerMapper examPaperAnswerMapper) {
    super(examPaperAnswerMapper);
    this.examPaperAnswerMapper = examPaperAnswerMapper;
  }

  @Resource
  ExamPaperMapper examPaperMapper;

  @Resource
  TextContentService textContentService;

  @Resource
  QuestionMapper questionMapper;

  @Resource
  ExamGradeMapper examGradeMapper;

  @Resource
  QuestionErrorMapper questionErrorMapper;

  @Resource
  CourseclassMapper courseclassMapper;

  @Resource
  CourseclassStuService courseclassStuService;
  @Resource
  SubjectMapper subjectMapper;

  @Resource
  SysUserMapper sysUserMapper;
  @Override
  public void calculateExamPaperAnswer(ExamPaperSubmitVM examPaperSubmitVM, SysUser user) {
    Date now = new Date();
    ExamPaper examPaper = examPaperMapper.selectByPrimaryKey(examPaperSubmitVM.getId());
    String content = textContentService.selectById(examPaper.getFrameTextContentId()).getContent();
    List<ExamPaperTitleItemObject> examPaperTitleItemObjects = JSONUtil.toList(content, ExamPaperTitleItemObject.class);
    List<Integer> questionIds = examPaperTitleItemObjects.stream().flatMap(t -> t.getQuestionItems().stream().map(q -> q.getId())).collect(Collectors.toList());
    List<Question> questions = questionMapper.selectByIds(questionIds);
    List<ExamPaperResponseItemVM> examPaperQuestionCustomerAnswers = questions.stream().map(question -> {
      ExamPaperResponseItemVM examPaperResponseItemVM = new ExamPaperResponseItemVM();
      examPaperResponseItemVM.setQuestionId(question.getId());
      examPaperResponseItemVM.setQuestionType(question.getQuestionType());
      examPaperResponseItemVM.setCorrect(question.getCorrect());
      String questionContent = textContentService.selectById(question.getInfoTextContentId()).getContent();
      QuestionVM questionObject = JSONUtil.toBean(questionContent, QuestionVM.class);
      examPaperResponseItemVM.setQuestionVM(questionObject);
      return examPaperResponseItemVM;
    }).collect(Collectors.toList());
    List<ExamPaperResponseItemVM> collect = examPaperTitleItemObjects.stream().flatMap(t -> t.getQuestionItems().stream().map(q ->
            {
              // 所有题目
              ExamPaperResponseItemVM question = examPaperQuestionCustomerAnswers.stream().filter(tq -> tq.getQuestionId().equals(q.getId())).findFirst().get();
              // 所有答题
              ExamPaperSubmitItemVM customerQuestionAnswer = examPaperSubmitVM.getAnswerList().stream().filter(tq -> tq.getQuestionId().equals(q.getId())).findFirst().orElse(null);
              Integer score = t.getScore();
              return ExamPaperQuestionCustomerAnswerFromVM(question, customerQuestionAnswer, score,user,examPaper,examPaperSubmitVM.getExamGradeId());
            })
    ).collect(Collectors.toList());
    int sum = collect.stream().mapToInt(t -> t.getScore()).sum();
    int doRight = (int) collect.stream().filter(t -> t.getDoRight() == 1).count();

    TextContent textContent = new TextContent();
    String studentAnswer = JSONUtil.toJsonStr(collect);
    textContent.setContent(studentAnswer);
    textContent.setCreateTime(now);
    textContentService.insertByFilter(textContent);

    ExamPaperAnswer examPaperAnswer = new ExamPaperAnswer();
    for (ExamPaperTitleItemObject examPaperTitleItemObject : examPaperTitleItemObjects) {
      if ("简答题".equals(examPaperTitleItemObject.getName()) || "填空题".equals(examPaperTitleItemObject.getName())) {
        examPaperAnswer.setStatus(1);
      }else {
        examPaperAnswer.setStatus(2);
      }
    }
    ExamGrade examGrade = examGradeMapper.selectByPrimaryKey(examPaperSubmitVM.getExamGradeId());

    examPaperAnswer.setPaperId(examPaper.getId());
    examPaperAnswer.setPaperName(examPaper.getName());
    examPaperAnswer.setCourseId(examPaper.getCourseId());
    examPaperAnswer.setPaperScore(examPaper.getScore());
    examPaperAnswer.setQuestionCount(examPaper.getQuestionCount());
    examPaperAnswer.setUserScore(sum);
    examPaperAnswer.setDoTime(examPaperSubmitVM.getDoTime() / 1000);
    examPaperAnswer.setUserId(user.getUsername());
    examPaperAnswer.setGradeId(examGrade.getGradeId());
    examPaperAnswer.setTextContentId(textContent.getId());
    examPaperAnswer.setQuestionCorrect(doRight);
    examPaperAnswer.setCreateTime(new Date());
    examPaperAnswer.setExamGradeId(examPaperSubmitVM.getExamGradeId());
    examPaperAnswerMapper.insertSelective(examPaperAnswer);
  }

  private ExamPaperResponseItemVM ExamPaperQuestionCustomerAnswerFromVM(ExamPaperResponseItemVM question, ExamPaperSubmitItemVM customerQuestionAnswer, Integer score,SysUser user,ExamPaper examPaper,Integer examGradeId) {
    question.setQuestionScore(score);
    if (null == customerQuestionAnswer) {
      question.setAnswer(null);
      /**
       * 0 错误
       * 1 正确
       * 3 待批改
       */
      question.setDoRight(0);
      question.setScore(0);
    } else {
      setSpecialFromVM(customerQuestionAnswer, question, score, user,examPaper,examGradeId);
    }
    return question;
  }


  private void setSpecialFromVM(ExamPaperSubmitItemVM customerQuestionAnswer, ExamPaperResponseItemVM question, Integer score,SysUser user,ExamPaper examPaper,Integer examGradeId) {
    QuestionTypeEnum questionTypeEnum = QuestionTypeEnum.fromCode(question.getQuestionType());
    QuestionError questionError = new QuestionError();
    switch (questionTypeEnum) {
      case SingleChoice:
      case TrueFalse:
        question.setAnswer(customerQuestionAnswer.getAnswer());
        question.setDoRight(question.getCorrect().equals(customerQuestionAnswer.getAnswer()) ? 1 : 0);
        question.setCorrect(question.getCorrect());
        question.setScore(question.getDoRight() != 0 ? score : 0);

        break;
      case MultipleChoice:
        question.setAnswerList(ExamUtil.contentToArray(customerQuestionAnswer.getAnswer()));
        question.setDoRight(question.getCorrect().equals(customerQuestionAnswer.getAnswer()) ? 1 : 0);
        question.setCorrect(question.getCorrect());
        question.setScore(question.getDoRight() != 0 ? score : 0);
        break;
      case GapFilling:
      default:
        question.setAnswer(customerQuestionAnswer.getAnswer());
        question.setDoRight(2);
        question.setScore(0);
        break;
    }
    if (question.getDoRight()==0){
      questionError.setQuestionId(question.getQuestionId());
      questionError.setAnswer(customerQuestionAnswer.getAnswer());
      questionError.setQuestionScore(String.valueOf(score));
      questionError.setIdcard(user.getUsername());
      questionError.setCreateTime(new Date());
      questionError.setScore(String.valueOf(question.getScore()));
      questionError.setCorrect(question.getCorrect());
      questionError.setPaperId(examPaper.getId());
      questionError.setCourseId(examPaper.getCourseId());
      questionError.setExamGradeId(examGradeId);
      questionError.setItemOrder(customerQuestionAnswer.getItemOrder());
      questionErrorMapper.insertSelective(questionError);
    }
  }

  @Override
  public PageInfo<ExamPaperAnswerPageResponseVM> scoreToVM(BasePage basePage, String idcard) {
    PageHelper.startPage(basePage.getPageIndex(), basePage.getPageSize());
    PageInfo<ExamPaperAnswer> pageInfo = new PageInfo<>(examPaperAnswerMapper.selectByIdcard(idcard));
    return PageInfoHelper.copyMap(pageInfo, t -> {
      ExamPaperAnswerPageResponseVM vm = modelMapper.map(t, ExamPaperAnswerPageResponseVM.class);
      Subject course = subjectMapper.selectByPrimaryKey(t.getCourseId());
      vm.setStatus(String.valueOf(t.getStatus()));
      vm.setCourseName(course.getName());
      vm.setDoTime(ExamUtil.secondToVM(t.getDoTime()));
      vm.setCreateTime(DateTimeUtil.dateFormat(t.getCreateTime()));
      return vm;
    });
  }

  @Override
  public PageInfo<ExamPaperAnswer> adminPage(ExamPaperAnswerPageRequestVM model) {
    PageHelper.startPage(model.getPageIndex(), model.getPageSize());
    return new PageInfo<>(examPaperAnswerMapper.page(model));
  }

  @Override
  public void answerCorrect(ExamPaperCorrectRequestVM requestVM) {
    Integer id = requestVM.getId();
    List<ExamPaperResponseItemVM> examPaperResponseItemVMS = requestVM.getExamPaperResponseItemVMS();
    ExamPaperAnswer examPaperAnswer = examPaperAnswerMapper.selectByPrimaryKey(id);
    List<ExamPaperResponseItemVM> collect = examPaperResponseItemVMS.stream().filter(t -> t.getQuestionType() >= 4).collect(Collectors.toList());
    List<ExamPaperResponseItemVM> rightCollet = collect.stream().filter(w -> w.getDoRight() == 1).collect(Collectors.toList());
    if (!rightCollet.isEmpty()) {
      int sum = rightCollet.stream().mapToInt(q -> q.getScore()).sum() + examPaperAnswer.getUserScore();
      Integer textContentId = examPaperAnswer.getTextContentId();
      TextContent textContent = new TextContent();
      textContent.setId(textContentId);
      textContent.setContent(JSONUtil.toJsonStr(examPaperResponseItemVMS));
      textContentService.updateByIdFilter(textContent);
      examPaperAnswer.setQuestionCorrect(rightCollet.size() + examPaperAnswer.getQuestionCorrect());
      examPaperAnswer.setUserScore(sum);
    }
    examPaperAnswer.setStatus(2);
    examPaperAnswerMapper.updateByPrimaryKeySelective(examPaperAnswer);
  }

  @Override
  public RestResponse toExamPaperAnswerVM(ExamPaperAnswerPageRequestVM requestVM) {
    ExamPaperAnswerResonseVM examPaperAnswerResonseVM = new ExamPaperAnswerResonseVM();
    ExamGrade examGrade = examGradeMapper.selectByPrimaryKey(requestVM.getExamGradeId());
    Courseclass grade = courseclassMapper.selectByPrimaryKey(requestVM.getGradeId());
    examPaperAnswerResonseVM.setExamName(examGrade.getTitle());
    examPaperAnswerResonseVM.setGradeName(grade.getClassName());
    List<String> limitDateTime = Arrays.asList(DateTimeUtil.dateFormat(examGrade.getStartTime()), DateTimeUtil.dateFormat(examGrade.getEndTime()));
    String examTime = StringUtils.join(limitDateTime, '-');
    examPaperAnswerResonseVM.setLimitTime(examTime);
    examPaperAnswerResonseVM.setTime(examGrade.getTime());
    String courseName = subjectMapper.selectByPrimaryKey(examGrade.getCourseId()).getName();
    examPaperAnswerResonseVM.setCourseName(courseName);
    List<String> idcards = courseclassStuService.selectStudentsByGradeId(requestVM.getGradeId());
    examPaperAnswerResonseVM.setExamCount(idcards.size());
    ExamPaper examPaper = examPaperMapper.selectByPrimaryKey(examGrade.getPaperId());
    examPaperAnswerResonseVM.setPaperCount(examPaper.getScore());
    examPaperAnswerResonseVM.setQuestionCount(examPaper.getQuestionCount());
    List<ExamPaperAnswer> examPaperAnswerList = examPaperAnswerMapper.select(requestVM.getGradeId(),requestVM.getExamGradeId());
    int size = examPaperAnswerList.stream().filter(t -> t.getStatus() == 1).collect(Collectors.toList()).size();
    examPaperAnswerResonseVM.setCorrectCount(size);
    examPaperAnswerResonseVM.setFinishedCount(examPaperAnswerList.size());

    List<KeyValue> list2 =  questionErrorMapper.selectByExamGradeId(requestVM.getExamGradeId());
    List<Integer> errorCountList = new ArrayList<>();
    List<Integer> errorQuestionsIdList = new ArrayList<>();
    for (KeyValue keyValue : list2) {
      errorQuestionsIdList.add(Integer.valueOf(keyValue.getName()));
      errorCountList.add(keyValue.getValue());
    }
    examPaperAnswerResonseVM.setErrorCountList(errorCountList);
    examPaperAnswerResonseVM.setErrorQuestionIdList(errorQuestionsIdList);

    if (requestVM.getStatus()!=null && requestVM.getStatus() !=3){
      PageInfo<ExamPaperAnswer> pageInfo = adminPage(requestVM);
      PageInfo<ExamPaperAnswerPageResponseVM> page = PageInfoHelper.copyMap(pageInfo, e -> {
        ExamPaperAnswerPageResponseVM vm = modelMapper.map(e, ExamPaperAnswerPageResponseVM.class);
        vm.setId(e.getId());
        vm.setDoTime(ExamUtil.secondToVM(e.getDoTime()));
        Subject course = subjectMapper.selectByPrimaryKey(vm.getCourseId());
        vm.setStatus(ExamPaperAnswerStatusEnum.fromCode(e.getStatus()).getName());
        vm.setUserScore(e.getUserScore());
        vm.setPaperScore(e.getPaperScore());
        vm.setCourseName(course.getName());
        vm.setCreateTime(DateTimeUtil.dateFormat(e.getCreateTime()));
        SysUser user = sysUserMapper.selectByUserName(e.getUserId());
        vm.setRealName(user.getRealName());
        vm.setIdcard(user.getUsername());
        return vm;
      });
      examPaperAnswerResonseVM.setList(page.getList());
      examPaperAnswerResonseVM.setTotal((int)pageInfo.getTotal());
      return RestResponse.ok(examPaperAnswerResonseVM);
    }else if(requestVM.getStatus()!=null && requestVM.getStatus()==3){
      List<ExamPaperAnswer> examPaperAnswers = examPaperAnswerMapper.select(requestVM.getGradeId(), requestVM.getExamGradeId());
      List<String> collect = examPaperAnswers.stream().map(t -> t.getUserId()).collect(Collectors.toList());
      PageInfo<CourseclassStu> gradeStudents = courseclassStuService.selectStudentsByNotCompleted(requestVM,collect);
      List<CourseclassStu> list = gradeStudents.getList();
      List<ExamPaperAnswerPageResponseVM> examPaperAnswerPageResponseVM = list.stream().map(t->{
        ExamPaperAnswerPageResponseVM vm = new ExamPaperAnswerPageResponseVM();
        SysUser user = sysUserMapper.selectByUserName(t.getUsername());
        vm.setRealName(user.getRealName());
        vm.setIdcard(user.getUsername());
        return vm;
      }).collect(Collectors.toList());
      examPaperAnswerResonseVM.setList(examPaperAnswerPageResponseVM);
      examPaperAnswerResonseVM.setTotal((int)gradeStudents.getTotal());
      return RestResponse.ok(examPaperAnswerResonseVM);
    }else {
      PageInfo<CourseclassStu> gradeStudents = courseclassStuService.selectStudents(requestVM);
      List<CourseclassStu> list = gradeStudents.getList();
      List<ExamPaperAnswer> examPaperAnswers = examPaperAnswerMapper.select(requestVM.getGradeId(), requestVM.getExamGradeId());
      List<ExamPaperAnswerPageResponseVM> examPaperAnswerPageResponseVM = list.stream().map(t->{
        ExamPaperAnswerPageResponseVM vm = new ExamPaperAnswerPageResponseVM();
        SysUser user = sysUserMapper.selectByUserName(t.getUsername());
        vm.setRealName(user.getRealName());
        vm.setIdcard(user.getUsername());
        vm.setRealName(user.getRealName());
        vm.setIdcard(user.getUsername());
        ExamPaperAnswer examPaperAnswerStream = examPaperAnswers.stream().filter(q -> q.getUserId().equals(t.getUsername())).findFirst().orElse(null);
        if (examPaperAnswerStream !=null){
          vm.setId(examPaperAnswerStream.getId());
          vm.setStatus(ExamPaperAnswerStatusEnum.fromCode(examPaperAnswerStream.getStatus()).getName());
          vm.setPaperScore(examPaperAnswerStream.getPaperScore());
          vm.setCreateTime(DateTimeUtil.dateFormat(examPaperAnswerStream.getCreateTime()));
          vm.setQuestionCorrect(examPaperAnswerStream.getQuestionCorrect());
          vm.setQuestionCount(examPaperAnswerStream.getQuestionCount());
          vm.setUserScore(examPaperAnswerStream.getUserScore());
          vm.setDoTime(ExamUtil.secondToVM(examPaperAnswerStream.getDoTime()));
        }
        return vm;
      }).collect(Collectors.toList());
      examPaperAnswerResonseVM.setList(examPaperAnswerPageResponseVM);
      examPaperAnswerResonseVM.setTotal((int)gradeStudents.getTotal());
      return RestResponse.ok(examPaperAnswerResonseVM);
    }
  }

  @Override
  public List<ExamPaperResponseItemVM> answerList(Integer id) {
    ExamPaperAnswer examPaperAnswer = selectById(id);
    Integer textContentId = examPaperAnswer.getTextContentId();
    TextContent textContent = textContentService.selectById(textContentId);
    String content = textContent.getContent();
    return JSONUtil.toList(content, ExamPaperResponseItemVM.class);
  }
}
