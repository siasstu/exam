package com.sias.admin.service;

import com.github.pagehelper.PageInfo;
import com.sias.admin.vm.exam.ExamGradePageRequestVM;
import com.sias.admin.vm.exam.ExamGradeRequestVM;
import com.sias.admin.vm.exam.ExamGradeResponseVM;
import com.sias.commons.service.BaseService;
import com.sias.admin.domain.ExamGrade;

import java.util.List;

/**
* @author 123
* @description 针对表【exam_grade】的数据库操作Service
* @createDate 2023-03-31 14:35:38
*/
public interface ExamGradeService extends BaseService<ExamGrade> {

  void releaseExam(ExamGradeRequestVM requestVM);

  PageInfo<ExamGrade> selectReleasedExam(ExamGradePageRequestVM requestVM);

  List<ExamGradeResponseVM> toExamGradeVM(ExamGradePageRequestVM requestVM);

  void deleteReleasedExam(Integer id);

  ExamGradeRequestVM selectReleasedExamById(Integer id);
}
