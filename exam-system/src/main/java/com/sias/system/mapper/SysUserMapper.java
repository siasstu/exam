package com.sias.system.mapper;

import com.sias.commons.mapper.BaseMapper;
import com.sias.commons.model.SysUser;
import com.sias.system.VM.UserSelectRequestVm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 123
* @description 针对表【sys_user】的数据库操作Mapper
* @createDate 2023-03-09 18:13:46
* @Entity generator.domain.SysUser
*/
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

  SysUser selectByUserName(String username);

  List<SysUser> page(UserSelectRequestVm requestVm);
  List<SysUser> selectByUserIds(@Param("ids") List<Long> ids);
  void save(@Param("userList") List<SysUser> userList);
  List<SysUser> selectStudentsByRoleId(UserSelectRequestVm requestVm);
}




