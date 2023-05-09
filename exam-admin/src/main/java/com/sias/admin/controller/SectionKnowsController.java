package com.sias.admin.controller;

import com.sias.admin.domain.Section;
import com.sias.admin.domain.SectionKnows;
import com.sias.admin.service.KnowsService;
import com.sias.admin.service.SectionKnowsService;
import com.sias.admin.service.SectionService;
import com.sias.commons.base.RestResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author 吴文杰
 * @version 1.0
 * @createTime 2023-03-13 12:19:11
 */
@RestController
@RequestMapping("/api/admin")
public class SectionKnowsController {
  @Resource
  SectionKnowsService sectionKnowsService;

  @Resource
  SectionService sectionService;

  @Resource
  KnowsService knowsService;

  @PostMapping("/section/knows/{id}")
  public RestResponse treeList(@PathVariable("id") long id) {
    List<Section> sectionKnowsList = sectionService.buildTreeKnows(id);
    return RestResponse.ok(sectionKnowsList);
  }

  @PostMapping("/section/knows/edit/{id}")
  public RestResponse treeList(@PathVariable("id") Integer id, SectionKnows sectionKnows) {
    if (sectionKnows.getId() != null) {
      sectionKnows.setCreateTime(new Date());
      sectionKnows.setSubjectId(id);
      sectionKnowsService.insertByFilter(sectionKnows);
    } else {
      sectionKnows.setUpdateTime(new Date());
      sectionKnowsService.updateByIdFilter(sectionKnows);
    }
    return RestResponse.ok();
  }

  @PostMapping("/section/knows/delete/{id}")
  public RestResponse treeList(@PathVariable("id") Integer id) {
    List<SectionKnows> knows = sectionKnowsService.selectByParentId(id);
    if (!knows.isEmpty()) {
      return RestResponse.fail(300, "请先删除相关章节!!");
    } else {
      sectionKnowsService.deleteById(id);
      return RestResponse.ok();
    }
  }

}
