package com.sias.commons.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author 吴文杰
 * @version 1.0
 * @createTime 2023-03-12 12:13:43
 */
public class TokenFailException extends AuthenticationException {
  public TokenFailException(String msg, Throwable cause) {
    super(msg, cause);
  }

  public TokenFailException(String msg) {
    super(msg);
  }
}
