package com.sias.admin.service.impl;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.sias.admin.domain.ClassStu;
import com.sias.admin.mapper.ClassStuMapper;
import com.sias.commons.base.BasePage;
import com.sias.commons.model.SysUser;
import com.sias.commons.model.SysUserRole;
import com.sias.commons.service.impl.BaseServiceImpl;
import com.sias.admin.domain.Class;
import com.sias.admin.mapper.ClassMapper;
import com.sias.admin.service.ClassService;
import com.sias.system.VM.UserSelectRequestVm;
import com.sias.system.service.SysRoleService;
import com.sias.system.service.SysUserRoleService;
import com.sias.system.service.SysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* @author 123
* @description 针对表【class】的数据库操作Service实现
* @createDate 2023-03-24 18:15:17
*/
@Service
public class ClassServiceImpl extends BaseServiceImpl<Class>
implements ClassService{

  private final ClassMapper classMapper;

  public ClassServiceImpl(ClassMapper classMapper) {
    super(classMapper);
    this.classMapper = classMapper;
  }

  @Resource
  ClassStuMapper classStuMapper;

  @Resource
  SysUserService sysUserService;

  @Resource
  SysUserRoleService sysUserRoleService;

  @Override
  public PageInfo<Class> page(BasePage basePage) {
    PageMethod.startPage(basePage.getPageIndex(), basePage.getPageSize());
    PageInfo<Class> classPageInfo = new PageInfo<>(classMapper.page());
    classPageInfo.getList().forEach(t-> {
      int size = classStuMapper.selectByClassId(t.getId()).size();
      t.setCount(size);
    });
    return classPageInfo;
  }

  @Override
  public PageInfo<SysUser> getStuByClass(UserSelectRequestVm requestVm) {
    PageMethod.startPage(requestVm.getPageIndex(), requestVm.getPageSize());
    return new PageInfo<>(classStuMapper.selectStudentsByClassId(requestVm));
  }

  @Override
  public PageInfo<SysUser> getStuByRole(UserSelectRequestVm requestVm) {
    PageMethod.startPage(requestVm.getPageIndex(), requestVm.getPageSize());
    return new PageInfo<>(sysUserService.selectStudentsByRoleId(requestVm));
  }

  @Override
  public void deleteByUsernameAndClassId(String username, Integer classId) {
    classStuMapper.deleteByUsernameAndClassId(username, classId);
  }

  @Override
  public void addStuToClass(String username, Integer classId) {
    classStuMapper.addStuToClass(username,classId);
  }

  @Override
  public List<Class> list() {
    return classMapper.page();
  }


}
