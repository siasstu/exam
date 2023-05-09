package com.sias.system.mapper;

import com.sias.commons.mapper.BaseMapper;
import com.sias.commons.model.SysUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 123
* @description 针对表【sys_user_role】的数据库操作Mapper
* @createDate 2023-03-10 12:15:21
* @Entity com.sias.domain.SysUserRole
*/
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

  void removeByUserIds(@Param("ids") Long[] ids);

  void removeByRoleIds(@Param("ids") List<Long> roleIds);

  List<SysUserRole> selectByRoleId(@Param("roleId") Integer roleId);
}
