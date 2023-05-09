package com.sias.commons.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author 吴文杰
 * @version 1.0
 * @createTime 2023-03-09 22:22:54
 */
public class UserCountLockException extends AuthenticationException {
  public UserCountLockException(String msg, Throwable cause) {
    super(msg, cause);
  }

  public UserCountLockException(String msg) {
    super(msg);
  }
}
