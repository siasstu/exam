package com.sias.system.service;

import com.github.pagehelper.PageInfo;
import com.sias.commons.base.BasePage;
import com.sias.commons.model.SysRole;
import com.sias.commons.service.BaseService;
import com.sias.system.VM.RoleSelectRequestVm;

import java.util.List;

/**
* @author 123
* @description 针对表【sys_role】的数据库操作Service
* @createDate 2023-03-10 12:15:08
*/
public interface SysRoleService extends BaseService<SysRole> {

  List<SysRole> selectByUserId(Long id);

  List<SysRole> roleList();

  PageInfo<SysRole> page(RoleSelectRequestVm requestVm);

  void removeByIds(List<Long> roleIds);
}
