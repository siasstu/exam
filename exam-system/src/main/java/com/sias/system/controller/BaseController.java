package com.sias.system.controller;

import com.sias.commons.model.SysUser;
import com.sias.system.context.WebContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author 吴文杰
 * @version 1.0
 * @createTime 2023-03-11 12:48:28
 */
public class BaseController {
  @Autowired
  protected WebContext webContext;

  /**
   * Gets current user.
   *
   * @return the current user
   */
  protected SysUser getCurrentUser() {
    return webContext.getCurrentUser();
  }
}
