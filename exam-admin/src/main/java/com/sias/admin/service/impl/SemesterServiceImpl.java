package com.sias.admin.service.impl;

import com.sias.commons.service.impl.BaseServiceImpl;
import com.sias.admin.domain.Semester;
import com.sias.admin.mapper.SemesterMapper;
import com.sias.admin.service.SemesterService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 123
* @description 针对表【semester】的数据库操作Service实现
* @createDate 2023-03-24 16:43:58
*/
@Service
public class SemesterServiceImpl extends BaseServiceImpl<Semester>
implements SemesterService{

  private final SemesterMapper semesterMapper;

  public SemesterServiceImpl(SemesterMapper semesterMapper) {
    super(semesterMapper);
    this.semesterMapper =semesterMapper;
  }

  @Override
  public List<Semester> list() {
    return semesterMapper.list();
  }
}
