package com.sias.system.mapper;

import com.sias.commons.mapper.BaseMapper;
import com.sias.commons.model.SysMenu;
import com.sias.commons.model.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
* @author 123
* @description 针对表【sys_menu】的数据库操作Mapper
* @createDate 2023-03-10 12:11:47
* @Entity com.sias.domain.SysMenu
*/
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {


  List<SysMenu> selectByRoleId(Long id);

  List<SysMenu> list();

  int count(@Param("id") Long id);

  void removeById(@Param("id") Long id);

}
