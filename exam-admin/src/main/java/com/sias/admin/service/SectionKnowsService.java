package com.sias.admin.service;

import com.sias.admin.domain.Section;
import com.sias.admin.domain.SectionKnows;
import com.sias.commons.service.BaseService;

import java.util.List;

/**
* @author 123
* @description 针对表【section_knows】的数据库操作Service
* @createDate 2023-03-13 12:08:39
*/
public interface SectionKnowsService extends BaseService<SectionKnows> {

  // List<Section> buildTreeKnows(long id);

  List<SectionKnows> selectByParentId(Integer parentId);
}
