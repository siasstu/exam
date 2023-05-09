package com.sias.admin.service;

import com.github.pagehelper.PageInfo;
import com.sias.admin.domain.Subject;
import com.sias.admin.vm.subject.SubjectSelectVM;
import com.sias.admin.vm.subject.SubjectVM;
import com.sias.commons.base.BasePage;
import com.sias.commons.service.BaseService;

import java.util.List;

/**
* @author 123
* @description 针对表【subject】的数据库操作Service
* @createDate 2023-03-13 12:08:39
*/
public interface SubjectService extends BaseService<Subject> {

  PageInfo<SubjectVM> page(SubjectSelectVM requestVM);

  List<Subject> selectByIds(List<Long> ids);

  List<SubjectVM> list(String username);
}
