package com.sias.commons.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author 吴文杰
 * @version 1.0
 * @createTime 2023-03-12 12:11:14
 */
public class TokenExpiredException extends AuthenticationException {
  public TokenExpiredException(String msg) {
    super(msg);
  }
  public TokenExpiredException(String msg, Throwable cause) {
    super(msg, cause);
  }
}
