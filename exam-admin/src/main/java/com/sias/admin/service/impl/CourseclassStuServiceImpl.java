package com.sias.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.sias.admin.domain.Courseclass;
import com.sias.admin.vm.ExamPaperAnswerPageRequestVM;
import com.sias.commons.service.impl.BaseServiceImpl;
import com.sias.admin.domain.CourseclassStu;
import com.sias.admin.mapper.CourseclassStuMapper;
import com.sias.admin.service.CourseclassStuService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author 123
* @description 针对表【courseclass_stu】的数据库操作Service实现
* @createDate 2023-03-30 22:17:40
*/
@Service
public class CourseclassStuServiceImpl extends BaseServiceImpl<CourseclassStu>
implements CourseclassStuService{

  private final CourseclassStuMapper courseclassStuMapper;

  public CourseclassStuServiceImpl(CourseclassStuMapper courseclassStuMapper) {
    super(courseclassStuMapper);
    this.courseclassStuMapper=courseclassStuMapper;
  }

  @Override
  public List<String> selectStudentsByGradeId(Integer gradeId) {
    List<CourseclassStu> students = courseclassStuMapper.selectByCourseClassId(gradeId);
    return students.stream().map(t->t.getUsername()).collect(Collectors.toList());
  }

  @Override
  public PageInfo<CourseclassStu> selectStudentsByNotCompleted(ExamPaperAnswerPageRequestVM requestVM, List<String> collect) {
    PageMethod.startPage(requestVM.getPageIndex(), requestVM.getPageSize());
    return new PageInfo<>(courseclassStuMapper.pageSelectByNotCompleted(requestVM.getGradeId(),collect));
  }

  @Override
  public PageInfo<CourseclassStu> selectStudents(ExamPaperAnswerPageRequestVM requestVM) {
    PageHelper.startPage(requestVM.getPageIndex(), requestVM.getPageSize());
    return new PageInfo<>(courseclassStuMapper.pageSelect(requestVM));
  }
}
