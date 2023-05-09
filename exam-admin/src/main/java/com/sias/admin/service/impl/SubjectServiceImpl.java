package com.sias.admin.service.impl;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.sias.admin.domain.Semester;
import com.sias.admin.domain.Subject;
import com.sias.admin.mapper.SemesterMapper;
import com.sias.admin.mapper.SubjectMapper;
import com.sias.admin.service.SubjectService;
import com.sias.admin.vm.subject.SubjectSelectVM;
import com.sias.admin.vm.subject.SubjectVM;
import com.sias.commons.service.impl.BaseServiceImpl;
import com.sias.commons.utils.PageInfoHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author 123
* @description 针对表【subject】的数据库操作Service实现
* @createDate 2023-03-13 12:08:39
*/
@Service
public class SubjectServiceImpl extends BaseServiceImpl<Subject>
implements SubjectService {
  private final SubjectMapper subjectMapper;

  public SubjectServiceImpl(SubjectMapper subjectMapper) {
    super(subjectMapper);
    this.subjectMapper = subjectMapper;
  }

  @Resource
  SemesterMapper semesterMapper;

  @Override
  public PageInfo<SubjectVM> page(SubjectSelectVM requestVM) {
    PageMethod.startPage(requestVM.getPageIndex(), requestVM.getPageSize());
    PageInfo<Subject> pageInfo = new PageInfo<>(subjectMapper.page(requestVM));
    return PageInfoHelper.copyMap(pageInfo, t -> {
      SubjectVM vm = modelMapper.map(t, SubjectVM.class);
      Semester semester = semesterMapper.selectByPrimaryKey(t.getSemesterId());
      vm.setSemesterName(semester.getName());
      return vm;
    });
  }

  @Override
  public List<Subject> selectByIds(List<Long> ids) {
    return subjectMapper.selectByIds(ids);
  }

  @Override
  public List<SubjectVM> list(String username) {
    return subjectMapper.list(username);
  }

}
