package com.sias.system.security;

import com.sias.commons.enums.SystemCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 吴文杰
 * @version 1.0
 * @createTime 2023-03-12 11:30:58
 */
@Component
public class AccessDenieHandleImpl implements AccessDeniedHandler {
  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
    RestUtil.response(response, SystemCode.AccessDenied);
  }
}
