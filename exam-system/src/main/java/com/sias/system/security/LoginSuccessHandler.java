package com.sias.system.security;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.useragent.Browser;
import cn.hutool.http.useragent.OS;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.sias.commons.enums.SystemCode;
import com.sias.commons.model.SysLoginLog;
import com.sias.commons.model.SysMenu;
import com.sias.commons.model.SysRole;
import com.sias.commons.model.SysUser;
import com.sias.commons.utils.AddressUtils;
import com.sias.commons.utils.IpUtils;
import com.sias.commons.utils.JwtUtils;
import com.sias.commons.utils.ServletUtils;
import com.sias.system.mapper.SysMenuMapper;
import com.sias.system.service.SysLoginLogService;
import com.sias.system.service.SysMenuService;
import com.sias.system.service.SysRoleService;
import com.sias.system.service.SysUserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 登录成功处理器
 *
 * @author 吴文杰
 * @version 1.0
 * @createTime 2023-03-09 21:42:35
 */
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

  @Resource
  SysUserService sysUserService;

  @Resource
  SysRoleService sysRoleService;

  @Resource
  SysMenuMapper sysMenuMapper;

  @Resource
  SysMenuService sysMenuService;


  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
    String name = authentication.getName();
    String token = JwtUtils.genJwtToken(name);
    SysUser currentUser = sysUserService.getByUserName(name);
    List<SysRole> sysRoles = sysRoleService.selectByUserId(currentUser.getId());
    Set<SysMenu> menuSet = new HashSet<>();
    for (SysRole role : sysRoles) {
      List<SysMenu> menuList = sysMenuMapper.selectByRoleId(role.getId());
      menuSet.addAll(menuList);
    }
    currentUser.setRoles(sysRoles.stream().map(SysRole::getName).collect(Collectors.joining(",")));
    List<SysMenu> sysMenus = new ArrayList<>(menuSet);
    sysMenus.sort(Comparator.comparing(SysMenu::getOrderNum));
    List<SysMenu> menuList = sysMenuService.buildTreeMenu(sysMenus);
    Map<String, Object> map = new HashMap<>();
    map.put("token",token);
    map.put("menuList",menuList);
    map.put("userInfo",currentUser);
    RestUtil.saveLoginLog(currentUser.getUsername(),request,SystemCode.OK);
    RestUtil.response(response, SystemCode.OK,map);
  }
}
