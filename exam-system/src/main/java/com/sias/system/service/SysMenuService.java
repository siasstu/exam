package com.sias.system.service;

import com.sias.commons.model.SysMenu;
import com.sias.commons.service.BaseService;

import java.util.List;

/**
* @author 123
* @description 针对表【sys_menu】的数据库操作Service
* @createDate 2023-03-10 12:11:47
*/
public interface SysMenuService extends BaseService<SysMenu> {

  List<SysMenu> buildTreeMenu(List<SysMenu> sysMenus);

  List<SysMenu> list();

  int count(Long id);

  void removeById(Long id);
}
