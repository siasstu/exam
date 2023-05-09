package com.sias.admin.service;

import com.github.pagehelper.PageInfo;
import com.sias.admin.vm.TestVM;
import com.sias.admin.vm.paper.ExamPaperEditRequestVM;
import com.sias.commons.base.BasePage;
import com.sias.commons.base.RestResponse;
import com.sias.commons.service.BaseService;
import com.sias.admin.domain.SmartTest;

/**
* @author 123
* @description 针对表【smart_test】的数据库操作Service
* @createDate 2023-04-01 17:21:35
*/
public interface SmartTestService extends BaseService<SmartTest> {
  ExamPaperEditRequestVM testToVM(Integer id);

  PageInfo page(BasePage basePage);

  RestResponse createTest(TestVM testVM);
}
