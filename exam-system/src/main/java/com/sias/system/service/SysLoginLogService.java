package com.sias.system.service;

import com.github.pagehelper.PageInfo;
import com.sias.commons.base.BasePage;
import com.sias.commons.model.SysLoginLog;
import com.sias.commons.service.BaseService;
import com.sias.system.VM.LogsSelectRequestVm;

import java.util.List;

/**
* @author 123
* @description 针对表【sys_login_log】的数据库操作Service
* @createDate 2023-03-18 19:08:05
*/
public interface SysLoginLogService extends BaseService<SysLoginLog> {

  PageInfo<SysLoginLog> page(LogsSelectRequestVm requestVm);

  void deleteByIds(List<Long> ids);
}
