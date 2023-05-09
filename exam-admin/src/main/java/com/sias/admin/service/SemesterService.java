package com.sias.admin.service;

import com.sias.commons.service.BaseService;
import com.sias.admin.domain.Semester;

import java.util.List;

/**
* @author 123
* @description 针对表【semester】的数据库操作Service
* @createDate 2023-03-24 16:43:58
*/
public interface SemesterService extends BaseService<Semester> {


  List<Semester> list();

}
