package com.sias.student.controller;

import com.github.pagehelper.PageInfo;
import com.sias.admin.service.SmartTestService;
import com.sias.admin.vm.TestVM;
import com.sias.admin.vm.paper.ExamPaperEditRequestVM;
import com.sias.commons.base.BasePage;
import com.sias.commons.base.RestResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/student/test")
public class StudentTestController {
  @Resource
  SmartTestService smartTestService;

  @PostMapping("/create")
  public RestResponse getStudentScore(@RequestBody TestVM testVM) {
    return smartTestService.createTest(testVM);
  }

  @PostMapping("/select/{id}")
  public RestResponse getStudentTestById(@PathVariable Integer id) {
    System.out.println(Thread.currentThread().getName());
    ExamPaperEditRequestVM testToVM = smartTestService.testToVM(id);
    return RestResponse.ok(testToVM);
  }

  @PostMapping("/page")
  public RestResponse getStudentTest(@RequestBody BasePage basePage) {
    PageInfo page = smartTestService.page(basePage);
    return RestResponse.ok(page);
  }
}