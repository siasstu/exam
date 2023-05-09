package com.sias.system.mapper;

import com.sias.commons.mapper.BaseMapper;
import com.sias.commons.model.SysRoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 123
* @description 针对表【sys_role_menu】的数据库操作Mapper
* @createDate 2023-03-10 12:15:15
* @Entity com.sias.domain.SysRoleMenu
*/
@Mapper
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {


  List<SysRoleMenu> list(@Param("roleId") Integer roleId);

  void removeByRoleId(Long id);
}
