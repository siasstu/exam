package com.sias.admin.controller;

import com.github.pagehelper.PageInfo;
import com.sias.admin.domain.Class;
import com.sias.admin.service.ClassService;
import com.sias.commons.base.BasePage;
import com.sias.commons.base.RestResponse;
import com.sias.commons.model.SysUser;
import com.sias.system.VM.UserSelectRequestVm;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 吴文杰
 * @version 1.0
 * @createTime 2023-03-24 18:20:28
 */
@RestController
@RequestMapping("/api/class")
public class ClassController {

  @Resource
  ClassService classService;

  @PostMapping("/{id}")
  public RestResponse selectClassById(@PathVariable("id") Integer id) {
    Class classInfo = classService.selectById(id);
    return RestResponse.ok(classInfo);
  }

  @PostMapping("/page")
  public RestResponse page(@RequestBody BasePage basePage) {
    PageInfo<Class> page = classService.page(basePage);
    return RestResponse.ok(page);
  }

  @PostMapping("/list")
  public RestResponse list() {
    List<Class> classes= classService.list();
    return RestResponse.ok(classes);
  }

  @PostMapping("/stu/page")
  public RestResponse classStuPage(@RequestBody UserSelectRequestVm requestVm) {
    PageInfo<SysUser> page = classService.getStuByClass(requestVm);
    return RestResponse.ok(page);
  }

  @PostMapping("/remove/stu/{username}/{classId}")
  public RestResponse removeStuFromClass(@PathVariable("username") String username,
                                         @PathVariable("classId") Integer classId) {
    classService.deleteByUsernameAndClassId(username, classId);
    return RestResponse.ok();
  }

  @PostMapping("/add/stu/{username}/{classId}")
  public RestResponse addStuToClass(@PathVariable("username") String username,
                                    @PathVariable("classId") Integer classId) {
    classService.addStuToClass(username, classId);
    return RestResponse.ok();
  }

  @PostMapping("/stuPage")
  public RestResponse stuPage(@RequestBody UserSelectRequestVm requestVm) {
    PageInfo<SysUser> page = classService.getStuByRole(requestVm);
    return RestResponse.ok(page);
  }

}
