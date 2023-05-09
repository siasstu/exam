package com.sias.admin.service.impl;

import com.sias.admin.domain.Knows;
import com.sias.admin.domain.Section;
import com.sias.admin.domain.SectionKnows;
import com.sias.admin.mapper.KnowsMapper;
import com.sias.admin.mapper.SectionKnowsMapper;
import com.sias.admin.mapper.SectionMapper;
import com.sias.admin.service.SectionKnowsService;
import com.sias.commons.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 123
 * @description 针对表【section_knows】的数据库操作Service实现
 * @createDate 2023-03-13 12:08:39
 */
@Service
public class SectionKnowsServiceImpl extends BaseServiceImpl<SectionKnows>
        implements SectionKnowsService {

  private final SectionKnowsMapper sectionKnowsMapper;

  public SectionKnowsServiceImpl(SectionKnowsMapper sectionKnowsMapper) {
    super(sectionKnowsMapper);
    this.sectionKnowsMapper = sectionKnowsMapper;
  }

  @Resource
  SectionMapper sectionMapper;

  @Resource
  KnowsMapper knowsMapper;

  // @Override
  // public List<Section> buildTreeKnows(long id) {
    // SelectSectionKnowsVM knowsVM = new SelectSectionKnowsVM();
    // knowsVM.setSubjectId(id);
    // List<SectionKnows> chapters = sectionKnowsMapper.list(knowsVM);
    // List<Long> sectionIds = chapters.stream().map(t -> t.getId()).collect(Collectors.toList());
    // knowsVM.setSubjectId(null);
    // knowsVM.setParentIds(sectionIds);
    // List<SectionKnows> sections = sectionKnowsMapper.list(knowsVM);
    // List<Long> knowsIds = sections.stream().map(t -> t.getId()).collect(Collectors.toList());
    // knowsVM.setParentIds(knowsIds);
    // List<SectionKnows> knows = sectionKnowsMapper.list(knowsVM);
    // List<SectionKnows> result = new ArrayList<>();
    // for (SectionKnows section : sections) {
    //   for (SectionKnows know : knows) {
    //     if (know.getParentId().equals(section.getId())) {
    //       section.getChildren().add(know);
    //     }
    //   }
    // }
    // for (SectionKnows chapter : chapters) {
    //   for (SectionKnows section : sections) {
    //     if (section.getParentId().equals(chapter.getId())) {
    //       chapter.getChildren().add(section);
    //     }
    //   }
    //   result.add(chapter);
    // }
    // return result;
  // }

  @Override
  public List<SectionKnows> selectByParentId(Integer parentId) {
    return sectionKnowsMapper.selectByParentId(parentId);
  }
}
