package com.sias.system.controller;

import com.github.pagehelper.PageInfo;
import com.sias.commons.annotation.Log;
import com.sias.commons.base.BasePage;
import com.sias.commons.base.RestResponse;
import com.sias.commons.model.SysRole;
import com.sias.commons.model.SysRoleMenu;
import com.sias.commons.model.SysUserRole;
import com.sias.system.VM.RoleSelectRequestVm;
import com.sias.system.service.SysRoleMenuService;
import com.sias.system.service.SysRoleService;
import com.sias.system.service.SysUserRoleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 吴文杰
 * @version 1.0
 * @createTime 2023-03-12 01:44:43
 */
@RestController
@RequestMapping("/api/role")
public class RoleController {

  @Resource
  SysRoleService sysRoleService;
  @Resource
  SysUserRoleService sysUserRoleService;
  @Resource
  SysRoleMenuService sysRoleMenuService;

  @PostMapping("/list")
  @PreAuthorize("hasAuthority('system:role:query')")
  public RestResponse list(){
    Map<String,Object> resultMap=new HashMap<>();
    List<SysRole> roleList = sysRoleService.roleList();
    resultMap.put("roleList",roleList);
    return RestResponse.ok(resultMap);
  }

  @PostMapping("/page")
  @PreAuthorize("hasAuthority('system:role:query')")
  public RestResponse page(@RequestBody RoleSelectRequestVm requestVm){
    PageInfo<SysRole> page = sysRoleService.page(requestVm);
    return RestResponse.ok(page);
  }

  @Log(title = "角色管理",value="修改角色信息")
  @PostMapping("/edit")
  public RestResponse edit(@RequestBody SysRole sysRole){
    if (sysRole.getId()==null||sysRole.getId()==-1){
      sysRole.setId(null);
      sysRole.setCreateTime(new Date());
      sysRoleService.insertByFilter(sysRole);
    }else {
      sysRole.setUpdateTime(new Date());
      sysRoleService.updateByIdFilter(sysRole);
    }
    return RestResponse.ok();
  }


  @PostMapping("/select/{id}")
  public RestResponse selectById(@PathVariable("id") Integer id){
    SysRole sysRole = sysRoleService.selectById(id);
    return RestResponse.ok(sysRole);
  }


  @Log(title = "角色管理",value="删除角色")
  @Transactional
  @PostMapping("/delete")
  public RestResponse delete(@RequestBody List<Long> roleIds){
    sysRoleService.removeByIds(roleIds);
    sysUserRoleService.removeByRoleIds(roleIds);
    return RestResponse.ok();
  }


  @Transactional
  @PostMapping("/grantRole/{userId}")
  @PreAuthorize("hasAuthority('system:user:role')")
  public RestResponse grantRole(@PathVariable("userId") Long userId, @RequestBody Long[] roleIds){
    List<SysUserRole> userRoleList=new ArrayList<>();
    Arrays.stream(roleIds).forEach(r -> {
      SysUserRole sysUserRole = new SysUserRole();
      sysUserRole.setRoleId(r);
      sysUserRole.setUserId(userId);
      userRoleList.add(sysUserRole);
    });
    Long[] ids = new Long[1];
    ids[0]=userId;
    sysUserRoleService.removeByUserIds(ids);
    userRoleList.forEach(t->{
      sysUserRoleService.insertByFilter(t);
    });
    return RestResponse.ok();
  }

  @PostMapping("/menus/list/{id}")
  public RestResponse menus(@PathVariable("id") Integer id){
    List<SysRoleMenu> roleMenuList = sysRoleMenuService.list(id);
    List<Long> menuIdList = roleMenuList.stream().map(p -> p.getMenuId()).collect(Collectors.toList());
    return RestResponse.ok(menuIdList);
  }

  @Log(title = "角色管理",value="修改角色菜单权限")
  @Transactional
  @PostMapping("/updateMenus/{id}")
  @PreAuthorize("hasAuthority('system:role:menu')")
  public RestResponse updateMenus(@PathVariable(value = "id")Long id, @RequestBody List<Long> menuIds){
    sysRoleMenuService.removeByRoleId(id);
    List<SysRoleMenu> sysRoleMenuList=new ArrayList<>();
    menuIds.forEach(menuId->{
      SysRoleMenu roleMenu=new SysRoleMenu();
      roleMenu.setRoleId(id);
      roleMenu.setMenuId(menuId);
      sysRoleMenuList.add(roleMenu);
    });
    sysRoleMenuList.forEach(t->sysRoleMenuService.insertByFilter(t));
    return RestResponse.ok();
  }

}
