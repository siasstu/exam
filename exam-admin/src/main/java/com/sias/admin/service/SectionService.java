package com.sias.admin.service;

import com.sias.commons.service.BaseService;
import com.sias.admin.domain.Section;

import java.util.List;

/**
* @author 123
* @description 针对表【section】的数据库操作Service
* @createDate 2023-03-22 16:18:56
*/
public interface SectionService extends BaseService<Section> {
  List<Section> buildTreeKnows(long id);
}
