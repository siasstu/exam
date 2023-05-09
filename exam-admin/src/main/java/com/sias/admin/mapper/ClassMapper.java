package com.sias.admin.mapper;

import com.sias.commons.mapper.BaseMapper;
import com.sias.admin.domain.Class;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author 123
* @description 针对表【class】的数据库操作Mapper
* @createDate 2023-03-24 18:15:17
* @Entity com.sias.admin.domain.Class
*/
@Mapper
public interface ClassMapper extends BaseMapper<Class> {
  List<Class> page();
}
