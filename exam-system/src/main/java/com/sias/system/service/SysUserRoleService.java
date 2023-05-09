package com.sias.system.service;


import com.sias.commons.model.SysUserRole;
import com.sias.commons.service.BaseService;
import com.sias.system.VM.UserSelectRequestVm;

import java.util.List;

/**
* @author 123
* @description 针对表【sys_user_role】的数据库操作Service
* @createDate 2023-03-10 12:15:21
*/
public interface SysUserRoleService extends BaseService<SysUserRole> {

  void removeByUserIds(Long[] ids);

  void removeByRoleIds(List<Long> roleIds);

}
