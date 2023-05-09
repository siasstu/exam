package com.sias.system.service.impl;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.sias.commons.base.BasePage;
import com.sias.commons.model.SysOperationLog;
import com.sias.commons.service.impl.BaseServiceImpl;
import com.sias.system.VM.LogsSelectRequestVm;
import com.sias.system.mapper.SysOperationLogMapper;
import com.sias.system.service.SysOperationLogService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 123
* @description 针对表【sys_opration_log】的数据库操作Service实现
* @createDate 2023-03-18 19:08:05
*/
@Service
public class SysOperationLogServiceImpl extends BaseServiceImpl<SysOperationLog>
implements SysOperationLogService {
  private final SysOperationLogMapper sysOperationLogMapper;

  public SysOperationLogServiceImpl(SysOperationLogMapper sysOperationLogMapper) {
    super(sysOperationLogMapper);
    this.sysOperationLogMapper = sysOperationLogMapper;
  }

  @Override
  public PageInfo<SysOperationLog> page(LogsSelectRequestVm requestVm) {
    PageMethod.startPage(requestVm.getPageIndex(),requestVm.getPageSize());
    return new PageInfo<>(sysOperationLogMapper.page(requestVm));
  }

  @Override
  public void deleteByIds(List<Long> ids) {
    sysOperationLogMapper.deleteByIds(ids);
  }
}
