package com.sias.admin.controller;

import com.github.pagehelper.PageInfo;
import com.sias.admin.domain.Courseclass;
import com.sias.admin.mapper.CourseclassMapper;
import com.sias.admin.mapper.CourseclassStuMapper;
import com.sias.admin.service.CourseclassService;
import com.sias.commons.base.BasePage;
import com.sias.commons.base.RestResponse;
import com.sias.commons.model.SysUser;
import com.sias.system.VM.UserSelectRequestVm;
import com.sias.system.controller.BaseController;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author 吴文杰
 * @version 1.0
 * @createTime 2023-03-30 22:21:24
 */
@RestController
@RequestMapping("/api/teacher")
public class CourseClassController extends BaseController {

  @Resource
  CourseclassService courseclassService;

  @Resource
  CourseclassMapper courseclassMapper;

  @PostMapping("/course/class/page")
  public RestResponse selectCourseClassPage(@RequestBody BasePage basePage){
    SysUser user = getCurrentUser();
    PageInfo<Courseclass> page = courseclassService.page(basePage,user.getUsername());
    return RestResponse.ok(page);
  }

  @PostMapping("/course/class/list")
  public RestResponse getMyCourseClassList(){
    SysUser user = getCurrentUser();
    List<Courseclass> page = courseclassMapper.page(user.getUsername());
    return RestResponse.ok(page);
  }


  @PostMapping("/course/class/edit")
  public RestResponse edit(@RequestBody Courseclass courseclass){
    SysUser user = getCurrentUser();
    courseclass.setCreateUser(user.getUsername());
    if (courseclass.getId()==null || courseclass.getId()==-1){
      courseclass.setCreateTime(new Date());
      courseclassService.insertByFilter(courseclass);
    }else {
      courseclass.setUpdateTime(new Date());
      courseclassService.updateByIdFilter(courseclass);
    }
    return RestResponse.ok();
  }

  @PostMapping("/course/class/{id}")
  public RestResponse getClassById(@PathVariable("id") Integer id){
    Courseclass courseclass = courseclassService.selectById(id);
    return RestResponse.ok(courseclass);
  }

  @PostMapping("/course/stu/page")
  public RestResponse classStuPage(@RequestBody UserSelectRequestVm requestVm) {
    PageInfo<SysUser> page = courseclassService.getStuByClass(requestVm);
    return RestResponse.ok(page);
  }

  @PostMapping("/remove/stu/{username}/{classId}")
  public RestResponse removeStuFromClass(@PathVariable("username") String username,
                                         @PathVariable("classId") Integer classId) {
    courseclassService.deleteByUsernameAndClassId(username, classId);
    return RestResponse.ok();
  }

  @PostMapping("/add/stu/{username}/{classId}")
  public RestResponse addStuToClass(@PathVariable("username") String username,
                                    @PathVariable("classId") Integer classId) {
    courseclassService.addStuToClass(username, classId);
    return RestResponse.ok();
  }

  @PostMapping("/add/stu/{id}")
  public RestResponse addStuToClass(@PathVariable("id")Integer id,@RequestBody List<Long> classIds) {
    courseclassService.addStuToClass(id,classIds);
    return RestResponse.ok();
  }

}
