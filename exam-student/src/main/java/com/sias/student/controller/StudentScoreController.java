package com.sias.student.controller;

import com.github.pagehelper.PageInfo;
import com.sias.admin.service.ExamPaperAnswerService;
import com.sias.admin.vm.ExamPaperAnswerPageResponseVM;
import com.sias.commons.base.BasePage;
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
 * creates 2023-03-2023 14:04
 */
@RestController
@RequestMapping("/api/student/score")
public class StudentScoreController extends BaseController {
    @Resource
    ExamPaperAnswerService examPaperAnswerService;

    @PostMapping("/page")
    public RestResponse<PageInfo<ExamPaperAnswerPageResponseVM>> getStudentScore(@RequestBody BasePage basePage) {
        SysUser currentUser = getCurrentUser();
        PageInfo<ExamPaperAnswerPageResponseVM> vm = examPaperAnswerService.scoreToVM(basePage,currentUser.getUsername());
        return RestResponse.ok(vm);
    }
}