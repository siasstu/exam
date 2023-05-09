package com.sias.system.mapper;

import com.sias.commons.mapper.BaseMapper;
import com.sias.commons.model.SysRole;
import com.sias.system.VM.RoleSelectRequestVm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 123
* @description 针对表【sys_role】的数据库操作Mapper
* @createDate 2023-03-10 12:15:08
* @Entity com.sias.domain.SysRole
*/
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {


  List<SysRole> selectByUserId(long userId);


  List<SysRole> page();

  List<SysRole> page(RoleSelectRequestVm requestVm);

  void removeByIds(@Param("ids") List<Long> roleIds);
}
