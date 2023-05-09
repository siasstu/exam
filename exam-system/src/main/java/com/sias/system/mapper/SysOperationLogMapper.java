package com.sias.system.mapper;

import com.sias.commons.mapper.BaseMapper;
import com.sias.commons.model.SysOperationLog;
import com.sias.system.VM.LogsSelectRequestVm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 123
* @description 针对表【sys_opration_log】的数据库操作Mapper
* @createDate 2023-03-18 19:08:05
* @Entity com.sias.commons.model.SysOprationLog
*/
@Mapper
public interface SysOperationLogMapper extends BaseMapper<SysOperationLog> {


  List<SysOperationLog> page(LogsSelectRequestVm requestVm);

  void deleteByIds(@Param("ids") List<Long> ids);
}
