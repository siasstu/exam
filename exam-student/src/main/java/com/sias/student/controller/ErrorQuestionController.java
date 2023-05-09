package com.sias.student.controller;

import com.github.pagehelper.PageInfo;
import com.sias.admin.service.QuestionErrorService;
import com.sias.admin.vm.ErrorQuestionVM;
import com.sias.admin.vm.question.QuestionPageRequestVM;
import com.sias.commons.base.RestResponse;
import com.sias.commons.model.SysUser;
import com.sias.system.controller.BaseController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 吴文杰
 * @version 1.0
 * creates 2023-03-2023 12:16
 */
@RestController
@RequestMapping("/api/student/question/error")
public class ErrorQuestionController extends BaseController {
  @Resource
  QuestionErrorService questionErrorService;

  @PostMapping("/page")
  public RestResponse errorQuestionPage(@RequestBody QuestionPageRequestVM requestVM){
    SysUser currentUser = getCurrentUser();
    PageInfo<ErrorQuestionVM> pageInfo = questionErrorService.errorQuestionForVM(currentUser, requestVM);
    return RestResponse.ok(pageInfo);
  }
}