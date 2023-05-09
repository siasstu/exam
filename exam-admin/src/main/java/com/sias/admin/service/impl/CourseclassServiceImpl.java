package com.sias.admin.service.impl;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.sias.admin.domain.ClassStu;
import com.sias.admin.domain.CourseclassStu;
import com.sias.admin.mapper.ClassStuMapper;
import com.sias.admin.mapper.CourseclassStuMapper;
import com.sias.admin.mapper.SubjectMapper;
import com.sias.commons.base.BasePage;
import com.sias.commons.model.SysUser;
import com.sias.commons.service.impl.BaseServiceImpl;
import com.sias.admin.domain.Courseclass;
import com.sias.admin.mapper.CourseclassMapper;
import com.sias.admin.service.CourseclassService;
import com.sias.system.VM.UserSelectRequestVm;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author 123
* @description 针对表【courseclass】的数据库操作Service实现
* @createDate 2023-03-30 22:17:40
*/
@Service
public class CourseclassServiceImpl extends BaseServiceImpl<Courseclass>
implements CourseclassService{

  private final CourseclassMapper courseclassMapper;

  public CourseclassServiceImpl(CourseclassMapper courseclassMapper) {
    super(courseclassMapper);
    this.courseclassMapper=courseclassMapper;
  }

  @Resource
  SubjectMapper subjectMapper;

  @Resource
  CourseclassStuMapper courseclassStuMapper;
  @Resource
  ClassStuMapper classStuMapper;
  @Override
  public PageInfo<Courseclass> page(BasePage basePage, String username) {
    PageMethod.startPage(basePage.getPageIndex(),basePage.getPageSize());
    PageInfo<Courseclass> pageInfo = new PageInfo<>(courseclassMapper.page(username));
    pageInfo.getList().forEach(t->{
      String name = subjectMapper.selectByPrimaryKey(t.getSubjectId()).getName();
      t.setSubjectName(name);
      List<CourseclassStu> list = courseclassStuMapper.selectByCourseClassId(t.getId());
      t.setClassCount(list.size());
    });
    return pageInfo;
  }

  @Override
  public PageInfo<SysUser> getStuByClass(UserSelectRequestVm requestVm) {
    PageMethod.startPage(requestVm.getPageIndex(), requestVm.getPageSize());
    return new PageInfo<>(courseclassStuMapper.selectStudentsByClassId(requestVm));
  }

  @Override
  public void deleteByUsernameAndClassId(String username, Integer classId) {
    courseclassStuMapper.deleteByUsernameAndClassId(username, classId);
  }

  @Override
  public void addStuToClass(String username, Integer classId) {
    courseclassStuMapper.addStuToClass(username,classId);
  }

  @Override
  public void addStuToClass(Integer id,List<Long> classIds) {
    List<ClassStu> objects = new ArrayList<>();
    classIds.forEach(t->{
      List<ClassStu> classStus = classStuMapper.selectByClassId(t);
      objects.addAll(classStus);
    });
    List<String> usernames = objects.stream().map(ClassStu::getUsername).collect(Collectors.toList());
    courseclassStuMapper.addStudents(id,usernames);
  }
}
