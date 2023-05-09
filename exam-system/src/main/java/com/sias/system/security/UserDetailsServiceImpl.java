package com.sias.system.security;

import com.sias.commons.enums.SystemCode;
import com.sias.commons.exception.UserCountLockException;
import com.sias.commons.model.SysUser;
import com.sias.system.service.SysUserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 自定义DetailService
 * @author 吴文杰
 * @version 1.0
 * @createTime 2023-03-09 22:12:09
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Resource
  SysUserService sysUserService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, UserCountLockException {
    SysUser sysUser = sysUserService.getByUserName(username);
    if (sysUser==null){
      throw new UsernameNotFoundException(SystemCode.AuthError.getMessage());
    }else if ("1".equals(sysUser.getStatus())){
      throw new UserCountLockException(SystemCode.UserCountLock.getMessage());
    }
    return new User(sysUser.getUsername(), sysUser.getPassword(), getUserAuthority(sysUser.getId()));
  }
  public List<GrantedAuthority> getUserAuthority(long userId) {
    String authority = sysUserService.getUserAuthorityInfo(userId);
    return AuthorityUtils.commaSeparatedStringToAuthorityList(authority);
  }
}
