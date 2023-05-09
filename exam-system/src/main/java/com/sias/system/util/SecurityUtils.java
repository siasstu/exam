package com.sias.system.util;

import com.sias.commons.model.SysUser;
import com.sias.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


/**
 * @author 123
 */
@Component
public class SecurityUtils {
  static SysUserService sysUserService;

  @Autowired
  public void setSysUserService(SysUserService sysUserService) {
    SecurityUtils.sysUserService = sysUserService;
  }

  /**
   * 用户账号
   **/
  public static String getUsername() {
      return getCurrentUser().getUsername();
  }


  /**
   * 获取用户姓名
   **/
  public static String getRealName() {
      return getCurrentUser().getRealName();
  }

  public static SysUser getCurrentUser() {
    String user = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return sysUserService.getByUserName(user);
  }
}
