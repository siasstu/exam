package com.sias.system.service.impl;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.sias.commons.model.SysLoginLog;
import com.sias.commons.service.impl.BaseServiceImpl;
import com.sias.system.VM.LogsSelectRequestVm;
import com.sias.system.mapper.SysLoginLogMapper;
import com.sias.system.service.SysLoginLogService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 123
* @description 针对表【sys_login_log】的数据库操作Service实现
* @createDate 2023-03-18 19:08:05
*/
@Service
public class SysLoginLogServiceImpl extends BaseServiceImpl<SysLoginLog>
implements SysLoginLogService{

  private final SysLoginLogMapper sysLoginLogMapper;

  public SysLoginLogServiceImpl(SysLoginLogMapper sysLoginLogMapper) {
    super(sysLoginLogMapper);
    this.sysLoginLogMapper = sysLoginLogMapper;
  }

  @Override
  public PageInfo<SysLoginLog> page(LogsSelectRequestVm requestVm) {
    PageMethod.startPage(requestVm.getPageIndex(),requestVm.getPageSize());
    return new PageInfo<>(sysLoginLogMapper.page(requestVm));
  }

  @Override
  public void deleteByIds(List<Long> ids) {
    sysLoginLogMapper.deleteByIds(ids);
  }
}
