package com.sias.system.security;

import com.sias.commons.enums.SystemCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 吴文杰
 * @version 1.0
 * @createTime 2023-03-10 10:59:08
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
    if (request.getAttribute("4001") != null && request.getAttribute("4001").equals(SystemCode.JWT_ERRCODE_EXPIRE.getMessage())) {
      RestUtil.response(response, SystemCode.JWT_ERRCODE_EXPIRE);
    }
    if (request.getAttribute("4002") != null && request.getAttribute("4002").equals(SystemCode.JWT_ERRCODE_FAIl.getMessage())) {
      RestUtil.response(response, SystemCode.JWT_ERRCODE_FAIl);
    }
    if (request.getAttribute("4003") != null && request.getAttribute("4003").equals(SystemCode.JWT_ERRCODE_NULL.getMessage())) {
      RestUtil.response(response, SystemCode.JWT_ERRCODE_NULL);
    }
  }
}
