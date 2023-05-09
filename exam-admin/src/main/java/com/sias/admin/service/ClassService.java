package com.sias.admin.service;

import com.github.pagehelper.PageInfo;
import com.sias.commons.base.BasePage;
import com.sias.commons.model.SysUser;
import com.sias.commons.service.BaseService;
import com.sias.admin.domain.Class;
import com.sias.system.VM.UserSelectRequestVm;

import java.util.List;
import java.util.Map;

/**
* @author 123
* @description 针对表【class】的数据库操作Service
* @createDate 2023-03-24 18:15:17
*/
public interface ClassService extends BaseService<Class> {

  PageInfo<Class> page(BasePage basePage);

  PageInfo<SysUser> getStuByClass(UserSelectRequestVm requestVm);

  PageInfo<SysUser> getStuByRole(UserSelectRequestVm requestVm);

  void deleteByUsernameAndClassId(String username, Integer classId);

  void addStuToClass(String username, Integer classId);

  List<Class> list();

}
