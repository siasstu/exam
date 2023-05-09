package com.sias.admin.controller;

import com.sias.admin.service.ExamPaperAnswerService;
import com.sias.admin.vm.ExamPaperAnswerPageRequestVM;
import com.sias.admin.vm.ExamPaperCorrectRequestVM;
import com.sias.admin.vm.ExamPaperResponseItemVM;
import com.sias.commons.base.RestResponse;
import com.sias.commons.model.SysUser;
import com.sias.system.controller.BaseController;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 吴文杰
 * @version 1.0
 * @createTime 2023-03-31 12:38:54
 */
@RestController
@RequestMapping(value = "/api/admin/examPaperAnswer")
public class ExamAnswerController extends BaseController {
  @Resource
  ExamPaperAnswerService examPaperAnswerService;

  @PostMapping("/page" )
  public RestResponse pageJudgeList(@RequestBody ExamPaperAnswerPageRequestVM requestVM) {
    return examPaperAnswerService.toExamPaperAnswerVM(requestVM);
  }
  @PostMapping("/answer/{id}" )
  public RestResponse<List<ExamPaperResponseItemVM>> answerList(@PathVariable("id") Integer id) {
    List<ExamPaperResponseItemVM> vm = examPaperAnswerService.answerList(id);
    return RestResponse.ok(vm);
  }
  @PostMapping("/answer/correct" )
  public RestResponse answerCorrect(@RequestBody ExamPaperCorrectRequestVM requestVM) {
    examPaperAnswerService.answerCorrect(requestVM);
    return RestResponse.ok();
  }
}
