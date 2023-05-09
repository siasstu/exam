package com.sias.system.service.impl;

import com.sias.commons.model.SysRoleMenu;
import com.sias.commons.service.impl.BaseServiceImpl;
import com.sias.system.mapper.SysRoleMenuMapper;
import com.sias.system.service.SysRoleMenuService;
import org.springframework.stereotype.Service;

import java.util.List;


/**
* @author 123
* @description 针对表【sys_role_menu】的数据库操作Service实现
* @createDate 2023-03-10 12:15:15
*/
@Service
public class SysRoleMenuServiceImpl extends BaseServiceImpl<SysRoleMenu>
implements SysRoleMenuService{
  private final SysRoleMenuMapper sysRoleMenuMapper;

  public SysRoleMenuServiceImpl(SysRoleMenuMapper sysRoleMenuMapper) {
    super(sysRoleMenuMapper);
    this.sysRoleMenuMapper = sysRoleMenuMapper;
  }

  @Override
  public List<SysRoleMenu> list(Integer roleId) {
    return sysRoleMenuMapper.list(roleId);
  }

  @Override
  public void removeByRoleId(Long id) {
    sysRoleMenuMapper.removeByRoleId(id);
  }
}
