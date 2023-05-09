package com.sias.admin.controller;

import com.github.pagehelper.PageInfo;
import com.sias.admin.domain.Question;
import com.sias.admin.service.QuestionService;
import com.sias.admin.vm.question.QuestionEditRequestVM;
import com.sias.admin.vm.question.QuestionPageRequestVM;
import com.sias.admin.vm.question.QuestionResponseVM;
import com.sias.commons.base.RestResponse;
import com.sias.commons.model.SysUser;
import com.sias.system.controller.BaseController;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author 吴文杰
 * @version 1.0
 * @createTime 2023-03-31 12:40:26
 */
@RestController
@RequestMapping( "/api/question")
public class QuestionController extends BaseController {
  @Resource
  QuestionService questionService;

  @PostMapping("/page")
  public RestResponse<PageInfo<QuestionResponseVM>> pageList(@RequestBody QuestionPageRequestVM model) {
    PageInfo<QuestionResponseVM> pageInfo = questionService.page(model);
    return RestResponse.ok(pageInfo);
  }

  @PostMapping("/edit")
  public RestResponse edit(@RequestBody QuestionEditRequestVM requestVM) {
    SysUser user = getCurrentUser();
    if (null == requestVM.getId()) {
      questionService.insertFullQuestion(requestVM,user.getUsername());
    } else {
      questionService.updateFullQuestion(requestVM);
    }
    return RestResponse.ok();
  }

  @PostMapping( "/select/{id}")
  public RestResponse<QuestionEditRequestVM> select(@PathVariable Integer id) {
    QuestionEditRequestVM vm = questionService.getQuestionEditRequestVM(id);
    return RestResponse.ok(vm);
  }
  @PostMapping("/delete/{id}")
  public RestResponse delete(@PathVariable Integer id) {
    Question question = questionService.selectById(id);
    question.setDeleted(true);
    questionService.updateByIdFilter(question);
    return RestResponse.ok();
  }
}
