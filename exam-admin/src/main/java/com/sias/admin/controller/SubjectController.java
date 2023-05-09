package com.sias.admin.controller;

import com.github.pagehelper.PageInfo;
import com.sias.admin.domain.Semester;
import com.sias.admin.domain.Subject;
import com.sias.admin.service.SemesterService;
import com.sias.admin.service.SubjectService;
import com.sias.admin.vm.subject.SubjectSelectVM;
import com.sias.admin.vm.subject.SubjectVM;
import com.sias.commons.base.RestResponse;
import com.sias.commons.model.SysUser;
import com.sias.system.controller.BaseController;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author 吴文杰
 * @version 1.0
 * @createTime 2023-03-13 15:52:19
 */
@RestController
@RequestMapping("/api")
public class SubjectController extends BaseController {

  @Resource
  SubjectService subjectService;

  @Resource
  SemesterService semesterService;

  @PostMapping("/admin/subject/page")
  public RestResponse page(@RequestBody SubjectSelectVM requestVM) {
    SysUser user = getCurrentUser();
    if (!"admin".equals(user.getUsername())) {
      requestVM.setUsername(user.getUsername());
    }
    PageInfo<SubjectVM> page = subjectService.page(requestVM);
    return RestResponse.ok(page);
  }

  @PostMapping("/teacher/subject/list")
  public RestResponse list() {
    SysUser user = getCurrentUser();
    List<SubjectVM> page = subjectService.list(user.getUsername());
    return RestResponse.ok(page);
  }

  @PostMapping("/subject/list")
  public RestResponse subjectList() {
    List<SubjectVM> page = subjectService.list(null);
    return RestResponse.ok(page);
  }

  @PostMapping("/admin/subject/semester/list")
  public RestResponse semesterList() {
    List<Semester> list = semesterService.list();
    return RestResponse.ok(list);
  }

  @PostMapping("/admin/subject/edit")
  public RestResponse edit(@RequestBody Subject subject) {
    if (subject.getId() == null || subject.getId() == -1) {
      SysUser currentUser = getCurrentUser();
      subject.setCreateTime(new Date());
      subject.setCreateUser(currentUser.getUsername());
      subjectService.insertByFilter(subject);
    } else {
      subject.setUpdateTime(new Date());
      subjectService.updateByIdFilter(subject);
    }
    return RestResponse.ok();
  }

  @PostMapping("/admin/subject/delete/{id}")
  public RestResponse delete(@PathVariable("id") Integer id) {
    Subject subject = subjectService.selectById(id);
    subject.setDeleted(true);
    subjectService.updateByIdFilter(subject);
    return RestResponse.ok();
  }

  @PostMapping("/admin/subject/select/{id}")
  public RestResponse selectById(@PathVariable("id") Integer id) {
    Subject subject = subjectService.selectById(id);
    return RestResponse.ok(subject);
  }

  @PostMapping("/admin/subject/delete")
  public RestResponse delete(@RequestBody List<Long> ids) {
    List<Subject> subjects = subjectService.selectByIds(ids);
    subjects.forEach(t -> t.setDeleted(true));
    subjects.forEach(t -> subjectService.updateByIdFilter(t));
    return RestResponse.ok();
  }

  @PostMapping("/teacher/myCourse")
  public RestResponse getMyTeachCourses(@RequestBody SubjectSelectVM requestVM) {
    SysUser user = getCurrentUser();
    requestVM.setUsername(user.getUsername());
    PageInfo<SubjectVM> page = subjectService.page(requestVM);
    return RestResponse.ok(page);
  }

}

