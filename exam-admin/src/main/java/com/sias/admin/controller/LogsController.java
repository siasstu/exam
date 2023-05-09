package com.sias.admin.controller;

import com.github.pagehelper.PageInfo;
import com.sias.commons.base.BasePage;
import com.sias.commons.base.RestResponse;
import com.sias.commons.model.SysLoginLog;
import com.sias.commons.model.SysOperationLog;
import com.sias.system.VM.LogsSelectRequestVm;
import com.sias.system.service.SysLoginLogService;
import com.sias.system.service.SysOperationLogService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 吴文杰
 * @version 1.0
 * @createTime 2023-03-20 11:35:03
 */
@RestController
@RequestMapping("/sys/logs")
public class LogsController {

  @Resource
  SysLoginLogService sysLoginLogService;

  @Resource
  SysOperationLogService sysOperationLogService;

  @PostMapping("/login/log/page")
  public RestResponse loginLogPage(@RequestBody LogsSelectRequestVm requestVm){
    PageInfo<SysLoginLog> page = sysLoginLogService.page(requestVm);
    return RestResponse.ok(page);
  }

  @PostMapping("/operation/log/page")
  public RestResponse operationLogPage(@RequestBody LogsSelectRequestVm requestVm){
    PageInfo<SysOperationLog> page = sysOperationLogService.page(requestVm);
    return RestResponse.ok(page);
  }

  @PostMapping("/login/log/delete")
  public RestResponse delLoginLog(@RequestBody List<Long> ids) {
    sysLoginLogService.deleteByIds(ids);
    return RestResponse.ok();
  }

  @PostMapping("/operation/log/delete")
  public RestResponse delOperationLog(@RequestBody List<Long> ids){
    sysOperationLogService.deleteByIds(ids);
    return RestResponse.ok();
  }
}
