package com.sias.admin.service.impl;

import com.sias.commons.service.impl.BaseServiceImpl;
import com.sias.admin.domain.ClassStu;
import com.sias.admin.mapper.ClassStuMapper;
import com.sias.admin.service.ClassStuService;
import org.springframework.stereotype.Service;

/**
* @author 123
* @description 针对表【class_stu】的数据库操作Service实现
* @createDate 2023-03-24 18:15:17
*/
@Service
public class ClassStuServiceImpl extends BaseServiceImpl<ClassStu>
implements ClassStuService{

  private final ClassStuMapper classStuMapper;

  public ClassStuServiceImpl(ClassStuMapper classStuMapper) {
    super(classStuMapper);
    this.classStuMapper = classStuMapper;
  }
}
