package com.sias.admin.controller;

import com.github.pagehelper.PageInfo;
import com.sias.admin.domain.ExamGrade;
import com.sias.admin.service.ExamGradeService;
import com.sias.admin.vm.exam.ExamGradePageRequestVM;
import com.sias.admin.vm.exam.ExamGradeRequestVM;
import com.sias.admin.vm.exam.ExamGradeResponseVM;
import com.sias.commons.base.RestResponse;
import com.sias.commons.model.SysUser;
import com.sias.system.controller.BaseController;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 吴文杰
 * @version 1.0
 * @createTime 2023-03-31 12:40:06
 */
@RestController
@RequestMapping("/api/exam/release")
public class ExamReleaseController extends BaseController {
  @Resource
  ExamGradeService examGradeService;

  @PostMapping("/edit")
  public RestResponse releaseExam(@RequestBody ExamGradeRequestVM requestVM){
    examGradeService.releaseExam(requestVM);
    return RestResponse.ok();
  }

  @PostMapping("/page")
  public RestResponse<PageInfo<ExamGrade>> selectReleasedExam(@RequestBody ExamGradePageRequestVM requestVM){
    SysUser user = getCurrentUser();
    if (!"admin".equals(user.getUsername())) {
      requestVM.setUsername(user.getUsername());
    }
    PageInfo<ExamGrade> pageInfo = examGradeService.selectReleasedExam(requestVM);
    return RestResponse.ok(pageInfo);
  }

  @PostMapping("/delete/{id}")
  public RestResponse deleteReleasedExam(@PathVariable Integer id){
    examGradeService.deleteReleasedExam(id);
    return RestResponse.ok();
  }

  @PostMapping("/select/{id}")
  public RestResponse<ExamGradeRequestVM> selectReleasedExam(@PathVariable Integer id){
    ExamGradeRequestVM examGrade = examGradeService.selectReleasedExamById(id);
    return RestResponse.ok(examGrade);
  }

  @PostMapping("/paper/page")
  public RestResponse<List<ExamGradeResponseVM>> selectReleasedExamPaper(@RequestBody ExamGradePageRequestVM requestVM){
    SysUser user = getCurrentUser();
    if (!"admin".equals(user.getUsername())) {
      requestVM.setUsername(user.getUsername());
    }
    List<ExamGradeResponseVM> examGradeResponseVM = examGradeService.toExamGradeVM(requestVM);
    return RestResponse.ok(examGradeResponseVM);
  }
}
