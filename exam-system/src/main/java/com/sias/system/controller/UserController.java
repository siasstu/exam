package com.sias.system.controller;

import com.github.pagehelper.PageInfo;
import com.sias.commons.annotation.Log;
import com.sias.commons.base.RestResponse;
import com.sias.commons.constant.Constant;
import com.sias.commons.model.SysUser;
import com.sias.system.VM.EditPasswordRequest;
import com.sias.system.VM.UserSelectRequestVm;
import com.sias.system.service.SysUserRoleService;
import com.sias.system.service.SysUserService;
import com.sias.system.util.DateUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

/**
 * @author 吴文杰
 * @version 1.0
 * @createTime 2023-03-09 18:32:09
 */
@RestController
@RequestMapping("/api/user")
public class UserController extends BaseController {
  @Resource
  SysUserService sysUserService;
  @Resource
  SysUserRoleService sysUserRoleService;
  @Resource
  BCryptPasswordEncoder bCryptPasswordEncoder;

  @Value("D:\\exam\\userAvatar\\")
  private String avatarImagesFilePath;

  @Log(title = "用户管理",value="修改用户信息")
  @PostMapping("/edit")
  @PreAuthorize("hasAuthority('system:user:edit')")
  public RestResponse edit(@RequestBody SysUser sysUser) {
    if (sysUser.getId() == null || sysUser.getId() == -1) {
      sysUser.setCreateTime(new Date());
      sysUser.setPassword(bCryptPasswordEncoder.encode(sysUser.getPassword()));
      sysUserService.insertByFilter(sysUser);
      return RestResponse.ok("添加成功");
    } else {
      sysUser.setUpdateTime(new Date());
      sysUserService.updateByIdFilter(sysUser);
      return RestResponse.ok(sysUser);
    }
  }
  @PostMapping("/select/{id}")
  public RestResponse selectById(@PathVariable("id") Integer id) {
    SysUser sysUser = sysUserService.selectById(id);
    return RestResponse.ok(sysUser);
  }

  @Log(title = "用户管理",value="修改密码")
  @PostMapping("/edit/password")
  public RestResponse editPassword(@RequestBody EditPasswordRequest request) {
    SysUser currentUser = getCurrentUser();
    if (bCryptPasswordEncoder.matches(request.getPassword(), currentUser.getPassword())) {
      currentUser.setPassword(bCryptPasswordEncoder.encode(request.getNewPassword()));
      currentUser.setUpdateTime(new Date());
      sysUserService.updateByIdFilter(currentUser);
      return RestResponse.ok();
    } else {
      return RestResponse.fail(300, "原密码错误");
    }
  }
  @PostMapping("/page")
  @PreAuthorize("hasAuthority('system:user:list')")
  public RestResponse page(@RequestBody UserSelectRequestVm requestVm) {
    PageInfo<SysUser> users = sysUserService.page(requestVm);
    return RestResponse.ok(users);
  }

  @PostMapping("/checkUsername")
  public RestResponse checkUserName(@RequestBody SysUser sysUser) {
    if (sysUserService.getByUserName(sysUser.getUsername()) == null) {
      return RestResponse.ok();
    } else {
      return RestResponse.fail(600, "用户名重复");
    }
  }

  @Log(title = "用户管理",value="修改用户状态")
  @PostMapping("/updateStatus/{id}/status/{status}")
  @PreAuthorize("hasAuthority('system:user:status')")
  public RestResponse updateStatus(@PathVariable(value = "id") Integer id, @PathVariable(value = "status") String status) {
    SysUser sysUser = sysUserService.selectById(id);
    sysUser.setStatus(status);
    sysUserService.updateByIdFilter(sysUser);
    return RestResponse.ok();
  }

  @Transactional
  @Log(title = "用户管理",value = "删除用户")
  @PostMapping("/delete")
  @PreAuthorize("hasAuthority('system:user:delete')")
  public RestResponse delete(@RequestBody Long[] ids) {
    sysUserService.removeByIds(Arrays.asList(ids));
    sysUserRoleService.removeByUserIds(ids);
    return RestResponse.ok();
  }

  @PostMapping("/resetPassword/{id}")
  @Log(title = "用户管理",value = "重置用户密码")
  @PreAuthorize("hasAuthority('system:user:resetPwd')")
  public RestResponse resetPassword(@PathVariable(value = "id") Integer id) {
    SysUser sysUser = sysUserService.selectById(id);
    sysUser.setPassword(bCryptPasswordEncoder.encode(Constant.DEFAULT_PASSWORD));
    sysUser.setUpdateTime(new Date());
    sysUserService.updateByIdFilter(sysUser);
    return RestResponse.ok();
  }

  /**
   * 修改用户头像
   * @return
   */
  @RequestMapping("/updateAvatar")
  @Log(title = "用户管理",value ="修改头像")
  @PreAuthorize("hasAuthority('system:user:edit')")
  public RestResponse updateAvatar(MultipartFile file) throws IOException {
    SysUser currentUser = getCurrentUser();
    if(!file.isEmpty()){
      String originalFilename = file.getOriginalFilename();
      String suffixName=originalFilename.substring(originalFilename.lastIndexOf("."));
      String newFileName= DateUtil.getCurrentDateStr()+suffixName;
      FileUtils.copyInputStreamToFile(file.getInputStream(),new File(avatarImagesFilePath+newFileName));
      currentUser.setUpdateTime(new Date());
      currentUser.setAvatar(newFileName);
    }
    sysUserService.updateByIdFilter(currentUser);
    return RestResponse.ok(currentUser);
  }

}
