package com.sias.system.service;


import com.sias.commons.model.SysRoleMenu;
import com.sias.commons.service.BaseService;

import java.util.List;

/**
* @author 123
* @description 针对表【sys_role_menu】的数据库操作Service
* @createDate 2023-03-10 12:15:15
*/
public interface SysRoleMenuService extends BaseService<SysRoleMenu> {

  List<SysRoleMenu> list(Integer roleId);

  void removeByRoleId(Long id);
}
