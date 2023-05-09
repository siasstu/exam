package com.sias.commons.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author 吴文杰
 * @version 1.0
 * @createTime 2023-03-12 12:13:03
 */
public class TokenNullException extends AuthenticationException {
  public TokenNullException(String msg, Throwable cause) {
    super(msg, cause);
  }

  public TokenNullException(String msg) {
    super(msg);
  }
}
