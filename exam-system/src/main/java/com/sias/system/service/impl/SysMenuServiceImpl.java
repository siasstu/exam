package com.sias.system.service.impl;

import com.sias.commons.model.SysMenu;
import com.sias.commons.service.impl.BaseServiceImpl;
import com.sias.system.mapper.SysMenuMapper;
import com.sias.system.service.SysMenuService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 123
 * @description 针对表【sys_menu】的数据库操作Service实现
 * @createDate 2023-03-10 12:11:47
 */
@Service
public class SysMenuServiceImpl extends BaseServiceImpl<SysMenu>
        implements SysMenuService {
  private final SysMenuMapper sysMenuMapper;

  public SysMenuServiceImpl(SysMenuMapper sysMenuMapper) {
    super(sysMenuMapper);
    this.sysMenuMapper = sysMenuMapper;
  }

  @Override
  public List<SysMenu> buildTreeMenu(List<SysMenu> sysMenus) {
    List<SysMenu> result = new ArrayList<>();
    for (SysMenu sysMenu : sysMenus) {
      for (SysMenu e:sysMenus){
        if (e.getParentId()==sysMenu.getId()){
          sysMenu.getChildren().add(e);
        }
      }
      if (sysMenu.getParentId()==0){
        result.add(sysMenu);
      }
    }
    return result;
  }

  @Override
  public List<SysMenu> list() {
    return sysMenuMapper.list();
  }

  @Override
  public int count(Long id) {
    return sysMenuMapper.count(id);
  }

  @Override
  public void removeById(Long id) {
    sysMenuMapper.removeById(id);
  }
}
