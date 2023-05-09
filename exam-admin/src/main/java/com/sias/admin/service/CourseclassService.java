package com.sias.admin.service;

import com.github.pagehelper.PageInfo;
import com.sias.commons.base.BasePage;
import com.sias.commons.model.SysUser;
import com.sias.commons.service.BaseService;
import com.sias.admin.domain.Courseclass;
import com.sias.system.VM.UserSelectRequestVm;

import java.util.List;

/**
* @author 123
* @description 针对表【courseclass】的数据库操作Service
* @createDate 2023-03-30 22:17:40
*/
public interface CourseclassService extends BaseService<Courseclass> {

  PageInfo<Courseclass> page(BasePage basePage, String username);

  PageInfo<SysUser> getStuByClass(UserSelectRequestVm requestVm);

  void deleteByUsernameAndClassId(String username, Integer classId);

  void addStuToClass(String username, Integer classId);
  void addStuToClass(Integer id,List<Long> classIds);
}
