package com.sias.admin.service;

import com.github.pagehelper.PageInfo;
import com.sias.admin.vm.AutoCreatePaperRequestVM;
import com.sias.admin.vm.paper.ExamPaperEditRequestVM;
import com.sias.admin.vm.paper.ExamPaperPageRequestVM;
import com.sias.admin.vm.paper.ExamResponseVM;
import com.sias.admin.vm.paper.StudentExamsResponseVM;
import com.sias.commons.base.RestResponse;
import com.sias.commons.model.SysUser;
import com.sias.commons.service.BaseService;
import com.sias.admin.domain.ExamPaper;

import java.util.List;

/**
* @author 123
* @description 针对表【exam_paper】的数据库操作Service
* @createDate 2023-03-31 14:01:52
*/
public interface ExamPaperService extends BaseService<ExamPaper> {

  PageInfo<ExamPaper> page(ExamPaperPageRequestVM model);

  ExamPaper savePaperFromVM(ExamPaperEditRequestVM model, String username);

  ExamPaperEditRequestVM examPaperToVM(Integer id);

  PageInfo<ExamResponseVM> toExamVM(ExamPaperPageRequestVM model);

  List<ExamPaper> selectPaper();

  void deletePaper(Integer id);

  List<StudentExamsResponseVM> examToVM(SysUser user);

  RestResponse autoCreatePaper(AutoCreatePaperRequestVM model, String username);
}
