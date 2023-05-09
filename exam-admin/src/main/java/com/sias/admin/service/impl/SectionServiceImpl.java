package com.sias.admin.service.impl;

import com.sias.admin.domain.Knows;
import com.sias.admin.mapper.KnowsMapper;
import com.sias.commons.service.impl.BaseServiceImpl;
import com.sias.admin.domain.Section;
import com.sias.admin.mapper.SectionMapper;
import com.sias.admin.service.SectionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author 123
* @description 针对表【section】的数据库操作Service实现
* @createDate 2023-03-22 16:18:56
*/
@Service
public class SectionServiceImpl extends BaseServiceImpl<Section>
implements SectionService{
  private final SectionMapper sectionMapper;
  public SectionServiceImpl(SectionMapper sectionMapper) {
    super(sectionMapper);
    this.sectionMapper = sectionMapper;
  }

  @Resource
  KnowsMapper knowsMapper;

  @Override
  public List<Section> buildTreeKnows(long id) {

    List<Section> sections = sectionMapper.selectBySubjectId(id);
    List<Integer> sectionIds = sections.stream().map(Section::getId).collect(Collectors.toList());
    List<Knows> knows = knowsMapper.selectBySectionId(sectionIds);
    for (Section section : sections) {
      for (Knows know : knows) {
        if (know.getSectionId().equals(section.getId())){
          section.getChildren().add(know);
        }
      }
    }
    return sections;
  }
}
