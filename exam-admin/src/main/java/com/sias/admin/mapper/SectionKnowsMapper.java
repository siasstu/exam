package com.sias.admin.mapper;

import com.sias.admin.domain.SectionKnows;
import com.sias.admin.vm.sectionknows.SelectSectionKnowsVM;
import com.sias.commons.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author 123
* @description 针对表【section_knows】的数据库操作Mapper
* @createDate 2023-03-13 12:08:39
* @Entity generator.domain.SectionKnows
*/
@Mapper
public interface SectionKnowsMapper extends BaseMapper<SectionKnows> {


  List<SectionKnows> list(SelectSectionKnowsVM knowsVM);

  List<SectionKnows> selectByParentId(Integer parentId);
}
