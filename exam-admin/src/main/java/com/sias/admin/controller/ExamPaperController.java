package com.sias.admin.controller;

import com.github.pagehelper.PageInfo;
import com.sias.admin.domain.ExamPaper;
import com.sias.admin.service.ExamPaperService;
import com.sias.admin.vm.AutoCreatePaperRequestVM;
import com.sias.admin.vm.paper.ExamPaperEditRequestVM;
import com.sias.admin.vm.paper.ExamPaperPageRequestVM;
import com.sias.admin.vm.paper.ExamResponseVM;
import com.sias.commons.base.RestResponse;
import com.sias.commons.model.SysUser;
import com.sias.system.controller.BaseController;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 吴文杰
 * @version 1.0
 * @createTime 2023-03-31 12:39:41
 */
@RestController("AdminExamPaperController")
@RequestMapping(value = "/api/exam/paper")
public class ExamPaperController extends BaseController {
  @Resource
  ExamPaperService examPaperService;

  @PostMapping("/page")
  public RestResponse<PageInfo<ExamResponseVM>> pageList(@RequestBody ExamPaperPageRequestVM model) {
    PageInfo<ExamResponseVM> page = examPaperService.toExamVM(model);
    return RestResponse.ok(page);
  }
  @PostMapping(value = "/edit")
  public RestResponse edit(@RequestBody ExamPaperEditRequestVM model) {
    SysUser user = getCurrentUser();
    examPaperService.savePaperFromVM(model,user.getUsername());
    return RestResponse.ok();
  }

  @PostMapping(value = "/autoCreatePaper")
  public RestResponse autoCreatePaper(@RequestBody AutoCreatePaperRequestVM model) {
    SysUser user = getCurrentUser();
    return examPaperService.autoCreatePaper(model, user.getUsername());
  }

  @PostMapping("/select/{id}")
  public RestResponse<ExamPaperEditRequestVM> select(@PathVariable Integer id) {
    ExamPaperEditRequestVM vm = examPaperService.examPaperToVM(id);
    return RestResponse.ok(vm);
  }
  @PostMapping("/selectAllPaper")
  public RestResponse<List<ExamPaper>> selectPaper() {
    List<ExamPaper> vm = examPaperService.selectPaper();
    return RestResponse.ok(vm);
  }
  @PostMapping("/delete/{id}")
  public RestResponse delete(@PathVariable Integer id) {
    examPaperService.deletePaper(id);
    return RestResponse.ok();
  }
}
