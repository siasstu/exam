package com.sias.student.controller;

import com.sias.admin.service.ExamPaperAnswerService;
import com.sias.admin.service.ExamPaperService;
import com.sias.admin.vm.ExamPaperSubmitVM;
import com.sias.admin.vm.paper.StudentExamsResponseVM;
import com.sias.commons.base.RestResponse;
import com.sias.commons.model.SysUser;
import com.sias.system.controller.BaseController;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController("StudentExamPaperController")
@RequestMapping(value = "/api/student/exam/paper")
public class ExamPaperStudentController extends BaseController {
    @Resource
    ExamPaperService examPaperService;
    @Resource
    ExamPaperAnswerService examPaperAnswerService;

    @PostMapping("/page")
    public RestResponse<List<StudentExamsResponseVM>> pageList() {
        SysUser user = getCurrentUser();
        List<StudentExamsResponseVM> responseVMS = examPaperService.examToVM(user);
        return RestResponse.ok(responseVMS);
    }

    @Transactional
    @PostMapping("/answerSubmit")
    public RestResponse answerSubmit(@RequestBody ExamPaperSubmitVM examPaperSubmitVM) {
        SysUser user = getCurrentUser();
        examPaperAnswerService.calculateExamPaperAnswer(examPaperSubmitVM,user);
        return RestResponse.ok();
    }
}