package com.sias.system.context;

import com.sias.commons.model.SysUser;
import com.sias.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author 吴文杰
 * @version 1.0
 * @createTime 2023-03-11 12:45:52
 */
@Component
public class WebContext {
  private final SysUserService sysUserService;

  @Autowired
  public WebContext(SysUserService sysUserService) {
    this.sysUserService = sysUserService;
  }

  public SysUser getCurrentUser() {
    String user = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return sysUserService.getByUserName(user);
  }
}
