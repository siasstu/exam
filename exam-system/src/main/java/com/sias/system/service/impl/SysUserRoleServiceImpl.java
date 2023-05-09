package com.sias.system.service.impl;

import com.sias.commons.model.SysUserRole;
import com.sias.commons.service.impl.BaseServiceImpl;
import com.sias.system.mapper.SysUserRoleMapper;
import com.sias.system.service.SysUserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 123
* @description 针对表【sys_user_role】的数据库操作Service实现
* @createDate 2023-03-10 12:15:21
*/
@Service
public class SysUserRoleServiceImpl extends BaseServiceImpl<SysUserRole>
implements SysUserRoleService{
    private final SysUserRoleMapper sysUserRoleMapper;

  public SysUserRoleServiceImpl(SysUserRoleMapper sysUserRoleMapper) {
    super(sysUserRoleMapper);
    this.sysUserRoleMapper = sysUserRoleMapper;
  }

  @Override
  public void removeByUserIds(Long[] ids) {
    sysUserRoleMapper.removeByUserIds(ids);
  }

  @Override
  public void removeByRoleIds(List<Long> roleIds) {
    sysUserRoleMapper.removeByRoleIds(roleIds);
  }

}
