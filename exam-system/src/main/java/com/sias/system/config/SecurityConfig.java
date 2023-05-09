package com.sias.system.config;

import com.sias.system.security.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

/**
 * @author 123
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Resource
  LoginSuccessHandler loginSuccessHandler;
  @Resource
  LoginFailureHandler loginFailureHandler;
  @Resource
  UserDetailsServiceImpl userDetailsServiceImpl;
  @Resource
  LogoutSuccessHandler logoutSuccessHandler;
  @Resource
  AuthenticationEntryPointImpl resetAuthenticationEntryPointImpl;

  @Resource
  AccessDenieHandleImpl accessDenieHandle;

  private static final String[] URL_WHITELIST = {"/captcha", "/password", "/image/**", "/test/**"};

  @Bean
  BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsServiceImpl);
  }

  @Bean
  public LoginAuthenticationFilter authenticationFilter() throws Exception {
    return new LoginAuthenticationFilter(authenticationManager());
  }

  @Override
  public void configure(WebSecurity web) {
    web.ignoring().antMatchers("/image/**");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // 开启跨域 以及csrf攻击 关闭
    http.cors().and().csrf().disable()
            // 登录登出配置
            .formLogin().loginProcessingUrl("/api/user/login").successHandler(loginSuccessHandler).failureHandler(loginFailureHandler)
            // session禁用配置
            .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // 无状态
            .and().logout().logoutUrl("/api/user/logout").logoutSuccessHandler(logoutSuccessHandler)
            // 拦截规则配置
            .and().authorizeRequests().antMatchers(URL_WHITELIST).permitAll()  // 白名单 放行
            // 认证
            .anyRequest().authenticated()
            .and().addFilter(authenticationFilter())
            .exceptionHandling().authenticationEntryPoint(resetAuthenticationEntryPointImpl).accessDeniedHandler(accessDenieHandle);
  }


}