package com.sias.system.service.impl;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.sias.commons.base.BasePage;
import com.sias.commons.constant.Constant;
import com.sias.commons.model.SysMenu;
import com.sias.commons.model.SysRole;
import com.sias.commons.model.SysUser;
import com.sias.commons.service.impl.BaseServiceImpl;
import com.sias.commons.utils.StringUtil;
import com.sias.system.VM.UserSelectRequestVm;
import com.sias.system.mapper.SysMenuMapper;
import com.sias.system.mapper.SysRoleMapper;
import com.sias.system.mapper.SysUserMapper;
import com.sias.system.service.SysUserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
* @author 123
* @description 针对表【sys_user】的数据库操作Service实现
* @createDate 2023-03-09 18:13:46
*/
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUser>
    implements SysUserService{

  @Resource
  private final SysUserMapper sysUserMapper;

  @Resource
  SysRoleMapper sysRoleMapper;

  @Resource
  SysMenuMapper sysMenuMapper;


  public SysUserServiceImpl(SysUserMapper sysUserMapper) {
    super(sysUserMapper);
    this.sysUserMapper = sysUserMapper;
  }

  @Override
  public SysUser getByUserName(String username) {
    return sysUserMapper.selectByUserName(username);
  }

  @Override
  public String getUserAuthorityInfo(long userId) {
    StringBuffer authority = new StringBuffer();
    List<SysRole> sysRoles = sysRoleMapper.selectByUserId(userId);
    if (!sysRoles.isEmpty()){
      String roleCodeStrs = sysRoles.stream().map(t -> "ROLE_" + t.getCode()).collect(Collectors.joining(","));
      authority.append(roleCodeStrs);
    }
    Set<String> menuSet = new HashSet<>();
    for (SysRole role : sysRoles){
      List<SysMenu> sysMenus = sysMenuMapper.selectByRoleId(role.getId());
      for (SysMenu sysMenu : sysMenus) {
        String perms = sysMenu.getPerms();
        if (StringUtil.isNotEmpty(perms)){
          menuSet.add(perms);
        }
      }
    }
    if (!menuSet.isEmpty()){
      authority.append(",");
      String menuCodeStrs = menuSet.stream().collect(Collectors.joining(","));
      authority.append(menuCodeStrs);
    }
    return authority.toString();
  }

  @Override
  public PageInfo<SysUser> page(UserSelectRequestVm requestVm) {
    PageMethod.startPage(requestVm.getPageIndex(), requestVm.getPageSize());
    PageInfo<SysUser> pageInfo = new PageInfo<>(sysUserMapper.page(requestVm));
    pageInfo.getList().forEach(t -> {
      List<SysRole> sysRoles = sysRoleMapper.selectByUserId(t.getId());
      t.setRoleList(sysRoles);
    });
    return pageInfo;
  }

  @Override
  public void removeByIds(List<Long> ids) {
    List<SysUser> users = sysUserMapper.selectByUserIds(ids);
    users.forEach(t->{
      t.setDeleted(true);
      sysUserMapper.updateByPrimaryKeySelective(t);
    });
  }

  @Override
  public void save(List<SysUser> userList) {
    sysUserMapper.save(userList);
  }

  @Override
  public List<SysUser> selectByIds(List<Long> ids) {
    return sysUserMapper.selectByUserIds(ids);
  }

  @Override
  public List<SysUser> selectStudentsByRoleId(UserSelectRequestVm requestVm) {
    return sysUserMapper.selectStudentsByRoleId(requestVm);
  }

}




