package com.sias.admin.service;

import com.github.pagehelper.PageInfo;
import com.sias.admin.domain.Courseclass;
import com.sias.admin.vm.ExamPaperAnswerPageRequestVM;
import com.sias.commons.service.BaseService;
import com.sias.admin.domain.CourseclassStu;

import java.util.List;

/**
* @author 123
* @description 针对表【courseclass_stu】的数据库操作Service
* @createDate 2023-03-30 22:17:40
*/
public interface CourseclassStuService extends BaseService<CourseclassStu> {

  List<String> selectStudentsByGradeId(Integer gradeId);

  PageInfo<CourseclassStu> selectStudentsByNotCompleted(ExamPaperAnswerPageRequestVM requestVM, List<String> collect);

  PageInfo<CourseclassStu> selectStudents(ExamPaperAnswerPageRequestVM requestVM);

}
