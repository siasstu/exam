package com.sias.system.service;



import com.github.pagehelper.PageInfo;
import com.sias.commons.base.BasePage;
import com.sias.commons.model.SysUser;
import com.sias.commons.service.BaseService;
import com.sias.system.VM.UserSelectRequestVm;

import java.util.List;

/**
* @author 123
* @description 针对表【sys_user】的数据库操作Service
* @createDate 2023-03-09 18:13:46
*/
public interface SysUserService extends BaseService<SysUser> {

  SysUser getByUserName(String username);

  String getUserAuthorityInfo(long userId);

  PageInfo<SysUser> page(UserSelectRequestVm requestVm);

  void removeByIds(List<Long> ids);

  void save(List<SysUser> userList);

  List<SysUser> selectByIds(List<Long> ids);

  List<SysUser> selectStudentsByRoleId(UserSelectRequestVm requestVm);
}
