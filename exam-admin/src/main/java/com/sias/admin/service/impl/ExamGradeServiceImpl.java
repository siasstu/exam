package com.sias.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sias.admin.domain.Courseclass;
import com.sias.admin.domain.CourseclassStu;
import com.sias.admin.domain.ExamGrade;
import com.sias.admin.domain.ExamPaperAnswer;
import com.sias.admin.mapper.*;
import com.sias.admin.service.ExamGradeService;
import com.sias.admin.vm.exam.ExamGradePageRequestVM;
import com.sias.admin.vm.exam.ExamGradeRequestVM;
import com.sias.admin.vm.exam.ExamGradeResponseVM;
import com.sias.commons.service.impl.BaseServiceImpl;
import com.sias.commons.utils.DateTimeUtil;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author 123
* @description 针对表【exam_grade】的数据库操作Service实现
* @createDate 2023-03-31 14:35:38
*/
@Service
public class ExamGradeServiceImpl extends BaseServiceImpl<ExamGrade>
implements ExamGradeService{
  private final ExamGradeMapper examGradeMapper;

  public ExamGradeServiceImpl(ExamGradeMapper examGradeMapper) {
    super(examGradeMapper);
    this.examGradeMapper=examGradeMapper;
  }

  @Resource
  CourseclassMapper courseclassMapper;

  @Resource
  CourseclassStuMapper courseclassStuMapper;

  @Resource
  ExamPaperAnswerMapper examPaperAnswerMapper;

  @Resource
  SubjectMapper subjectMapper;

  @Override
  public void releaseExam(ExamGradeRequestVM requestVM) {
    Date date = new Date();
    ExamGrade examGrade = modelMapper.map(requestVM, ExamGrade.class);
    Courseclass courseclass = courseclassMapper.selectByPrimaryKey(requestVM.getGradeId());
    if (requestVM.getId() ==null) {
      examGrade.setCreateTime(date);
      examGrade.setCourseId(courseclass.getSubjectId());
      List<String> dateTimes = requestVM.getLimitDateTime();
      examGrade.setStartTime(DateTimeUtil.parse(dateTimes.get(0), DateTimeUtil.STANDER_FORMAT));
      examGrade.setEndTime(DateTimeUtil.parse(dateTimes.get(1), DateTimeUtil.STANDER_FORMAT));
      examGradeMapper.insert(examGrade);
    }
  }

  @Override
  public PageInfo<ExamGrade> selectReleasedExam(ExamGradePageRequestVM requestVM) {
    PageHelper.startPage(requestVM.getPageIndex(), requestVM.getPageSize());
    if (requestVM.getUsername()!=null){
      List<Courseclass> courseclasses = courseclassMapper.page(requestVM.getUsername());
      List<Integer> list = courseclasses.stream().map(Courseclass::getId).collect(Collectors.toList());
      if (list.size()>0){
        requestVM.setIds(list);
      }else {
        return new PageInfo<>();
      }
    }
    return new PageInfo<>(examGradeMapper.page(requestVM));
  }

  @Override
  public List<ExamGradeResponseVM> toExamGradeVM(ExamGradePageRequestVM requestVM) {
    PageInfo<ExamGrade> pageInfo = selectReleasedExam(requestVM);
    List<ExamGrade> list = pageInfo.getList();
    return list.stream().map(t->{
      List<CourseclassStu> students = courseclassStuMapper.selectByCourseClassId(t.getGradeId());
      List<ExamPaperAnswer> examPaperAnswers = examPaperAnswerMapper.select(t.getGradeId(),t.getId());
      int size = examPaperAnswers.size();
      int count = (int) examPaperAnswers.stream().filter(q -> q.getStatus().equals(1)).count();
      ExamGradeResponseVM vm = new ExamGradeResponseVM();
      vm.setGradeId(t.getGradeId());
      vm.setClassNumber(students.size());
      vm.setExamGradeId(t.getId());
      vm.setCorrectingNumber(count);
      vm.setCourseName(subjectMapper.selectByPrimaryKey(t.getCourseId()).getName());
      vm.setFinishedNumber(size);
      vm.setTitle(t.getTitle());
      List<String> limitDateTime = Arrays.asList(DateTimeUtil.dateFormat(t.getStartTime()), DateTimeUtil.dateFormat(t.getEndTime()));
      String join = StringUtils.join(limitDateTime, ' ');
      vm.setExamTime(join);
      vm.setName(courseclassMapper.selectByPrimaryKey(t.getGradeId()).getClassName());
      return vm;
    }).collect(Collectors.toList());
  }

  @Override
  public void deleteReleasedExam(Integer id) {
   examGradeMapper.deleteByPrimaryKey(id);
  }

  @Override
  public ExamGradeRequestVM selectReleasedExamById(Integer id) {
    ExamGrade examGrade = examGradeMapper.selectByPrimaryKey(id);
    ExamGradeRequestVM vm = modelMapper.map(examGrade, ExamGradeRequestVM.class);
    List<String> limitDateTime = Arrays.asList(DateTimeUtil.dateFormat(examGrade.getStartTime()), DateTimeUtil.dateFormat(examGrade.getEndTime()));
    vm.setLimitDateTime(limitDateTime);
    return vm;
  }
}
