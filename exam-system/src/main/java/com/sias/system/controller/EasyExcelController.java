package com.sias.system.controller;

import com.alibaba.excel.EasyExcel;
import com.sias.commons.model.SysUser;
import com.sias.system.listener.UploadDataListener;
import com.sias.system.service.SysUserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author 吴文杰
 * @version 1.0
 * @createTime 2023-03-14 22:38:33
 */
@RestController
@RequestMapping("/api/admin/model")
public class EasyExcelController {

  @Resource
  BCryptPasswordEncoder bCryptPasswordEncoder;

  @Resource
  SysUserService sysUserService;

  /**
   * 批量导入用户
   *
   * @param file
   * @return
   * @throws IOException
   */
  @PostMapping("/upload")
  public String upload(MultipartFile file) throws IOException {
    EasyExcel.read(file.getInputStream(), SysUser.class, new UploadDataListener(sysUserService,bCryptPasswordEncoder)).sheet().doRead();
    return "success";
  }

  /**
   * 用户模板下载
   *
   * @param response
   * @throws IOException
   */
  @PostMapping("/download")
  public void download(HttpServletResponse response,@RequestBody List<Long> ids) throws IOException {
    if (!ids.isEmpty()){
      List<SysUser> userList = sysUserService.selectByIds(ids);
      response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
      response.setCharacterEncoding("utf-8");
      String fileName = URLEncoder.encode("用户信息", "UTF-8").replaceAll("\\+", "%20");
      response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
      EasyExcel.write(response.getOutputStream(), SysUser.class).sheet("用户信息").doWrite(userList);
    }else {
      List<SysUser> userList = new ArrayList<>();
      userList.add(new SysUser("201xxxxxxxx29", "84xxxxx70@xxx.com", "173*****123", "男", new Date(), "张三","20**级***班"));
      userList.add(new SysUser("201xxxxxxxx25", "84xxxxx70@xxx.com", "173*****123", "女", new Date(), "李四","20**级***班"));
      response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
      response.setCharacterEncoding("utf-8");
      String fileName = URLEncoder.encode("导入用户模板", "UTF-8").replaceAll("\\+", "%20");
      response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
      EasyExcel.write(response.getOutputStream(), SysUser.class).sheet("导入用户模板").doWrite(userList);
    }
  }
}
