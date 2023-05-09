package com.sias.system.controller;

import com.sias.commons.annotation.Log;
import com.sias.commons.base.RestResponse;
import com.sias.commons.model.SysMenu;
import com.sias.system.service.SysMenuService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author 吴文杰
 * @version 1.0
 * @createTime 2023-03-12 02:42:13
 */
@RestController
@RequestMapping("/api/menu")
public class MenuController {

  @Resource
  SysMenuService sysMenuService;

  @PostMapping("/treeList")
  public RestResponse treeList(){
    List<SysMenu> list = sysMenuService.list();
    return RestResponse.ok(sysMenuService.buildTreeMenu(list));
  }

  @PostMapping("/edit")
  @Log(title = "菜单管理", value ="修改菜单")
  public RestResponse edit(@RequestBody SysMenu sysMenu){
    if (sysMenu.getId()==null){
      sysMenu.setCreateTime(new Date());
      sysMenuService.insertByFilter(sysMenu);
    }else {
      sysMenu.setUpdateTime(new Date());
      sysMenuService.updateByIdFilter(sysMenu);
    }
    return RestResponse.ok();
  }


  @PostMapping("/delete/{id}")
  @Log(title = "菜单管理", value ="删除菜单")
  @PreAuthorize("hasAuthority('system:menu:delete')")
  public RestResponse delete(@PathVariable(value = "id")Long id){
    int count = sysMenuService.count(id);
    if(count>0){
      return RestResponse.fail(300,"请先删除子菜单！");
    }
    sysMenuService.removeById(id);
    return RestResponse.ok();
  }

  @PostMapping("/select/{id}")
  public RestResponse selectById(@PathVariable(value = "id") Integer id){
    SysMenu sysMenu = sysMenuService.selectById(id);
    return RestResponse.ok(sysMenu);
  }


}
