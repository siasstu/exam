package com.sias.system.security;

import com.sias.commons.constant.Constant;
import com.sias.commons.enums.SystemCode;
import com.sias.commons.exception.TokenExpiredException;
import com.sias.commons.exception.TokenFailException;
import com.sias.commons.exception.TokenNullException;
import com.sias.commons.model.CheckResult;
import com.sias.commons.model.SysUser;
import com.sias.commons.utils.JwtUtils;
import com.sias.commons.utils.StringUtil;
import com.sias.system.service.SysUserService;
import io.jsonwebtoken.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 吴文杰
 * @version 1.0
 * @createTime 2023-03-11 15:08:49
 */
public class LoginAuthenticationFilter  extends BasicAuthenticationFilter {

  @Resource
  SysUserService sysUserService;
  private static final String[] URL_WHITELIST = {"/logout", "/captcha", "/password", "/image/**", "/test/**","/test"};
  @Resource
  UserDetailsServiceImpl myUserDetailsServiceImpl;

  public LoginAuthenticationFilter(AuthenticationManager authenticationManager) {
    super(authenticationManager);
  }

  @Override
  protected void onUnsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
    super.onUnsuccessfulAuthentication(request, response, failed);
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
    AntPathMatcher matcher = new AntPathMatcher();
    boolean match = matcher.match("/image/**", request.getRequestURI());
    String token = request.getHeader("token");
    if(match){
      chain.doFilter(request,response);
      return;
    }
    if (StringUtil.isEmpty(token)){
      request.setAttribute("4003", SystemCode.JWT_ERRCODE_NULL.getMessage());
      throw new TokenNullException(SystemCode.JWT_ERRCODE_NULL.getMessage());
    }
    CheckResult validateJWT = JwtUtils.validateJWT(token);
    if (!validateJWT.isSuccess()) {
      switch (validateJWT.getErrCode()) {
        case Constant.JWT_ERRCODE_FAIL:
          request.setAttribute("4002",SystemCode.JWT_ERRCODE_FAIl.getMessage());
          throw new TokenFailException(SystemCode.JWT_ERRCODE_FAIl.getMessage());
        case Constant.JWT_ERRCODE_EXPIRE:
          request.setAttribute("4001",SystemCode.JWT_ERRCODE_EXPIRE.getMessage());
          throw new TokenExpiredException(SystemCode.JWT_ERRCODE_EXPIRE.getMessage());
      }
    }
    Claims claims = JwtUtils.parseJWT(token);
    String username = claims.getSubject();
    SysUser user = sysUserService.getByUserName(username);
    UsernamePasswordAuthenticationToken userAuthToken = new UsernamePasswordAuthenticationToken(username, null, myUserDetailsServiceImpl.getUserAuthority(user.getId()));
    SecurityContextHolder.getContext().setAuthentication(userAuthToken);
    chain.doFilter(request, response);
  }
}
