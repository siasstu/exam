package com.sias.admin.mapper;

import com.sias.commons.mapper.BaseMapper;
import com.sias.admin.domain.Semester;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author 123
* @description 针对表【semester】的数据库操作Mapper
* @createDate 2023-03-24 16:43:58
* @Entity com.sias.admin.domain.Semester
*/
@Mapper
public interface SemesterMapper extends BaseMapper<Semester> {


  List<Semester> list();

}
