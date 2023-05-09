package com.sias.system.security;

import com.sias.commons.enums.SystemCode;
import com.sias.commons.exception.UserCountLockException;
import com.sias.commons.model.SysUser;
import com.sias.commons.utils.JwtUtils;
import com.sias.system.service.SysUserService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 吴文杰
 * @version 1.0
 * @createTime 2023-03-09 21:51:20
 */
@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {


  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
    String username = request.getParameter("username");
    if (exception instanceof BadCredentialsException) {
      RestUtil.response(response, SystemCode.AuthError);
      RestUtil.saveLoginLog(username,request,SystemCode.AuthError);
    }else if (exception.getCause() instanceof UserCountLockException){
      RestUtil.response(response,SystemCode.UserCountLock);
      RestUtil.saveLoginLog(username,request,SystemCode.UserCountLock);
    }
  }
}
