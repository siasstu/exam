package com.sias.system.service.impl;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.sias.commons.base.BasePage;
import com.sias.commons.model.SysRole;
import com.sias.commons.service.impl.BaseServiceImpl;
import com.sias.system.VM.RoleSelectRequestVm;
import com.sias.system.mapper.SysRoleMapper;
import com.sias.system.service.SysRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 123
 * @description 针对表【sys_role】的数据库操作Service实现
 * @createDate 2023-03-10 12:15:08
 */
@Service
public class SysRoleServiceImpl extends BaseServiceImpl<SysRole>
        implements SysRoleService {
  private final SysRoleMapper sysRoleMapper;

  public SysRoleServiceImpl(SysRoleMapper sysRoleMapper) {
    super(sysRoleMapper);
    this.sysRoleMapper = sysRoleMapper;
  }

  @Override
  public List<SysRole> selectByUserId(Long id) {
    return sysRoleMapper.selectByUserId(id);
  }

  @Override
  public List<SysRole> roleList() {
    return sysRoleMapper.page();
  }

  @Override
  public PageInfo<SysRole> page(RoleSelectRequestVm requestVm) {
    PageMethod.startPage(requestVm.getPageIndex(), requestVm.getPageSize());
    return new PageInfo<>(sysRoleMapper.page(requestVm));
  }

  @Override
  public void removeByIds(List<Long> roleIds) {
    sysRoleMapper.removeByIds(roleIds);
  }
}
