package com.sias.admin.mapper;

import com.sias.commons.mapper.BaseMapper;
import com.sias.admin.domain.Section;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author 123
* @description 针对表【section】的数据库操作Mapper
* @createDate 2023-03-22 16:18:56
* @Entity com.sias.admin.domain.Section
*/
@Mapper
public interface SectionMapper extends BaseMapper<Section> {


  List<Section> selectBySubjectId(long id);
}
