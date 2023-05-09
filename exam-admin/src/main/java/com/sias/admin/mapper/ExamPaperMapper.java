package com.sias.admin.mapper;

import com.sias.admin.vm.paper.ExamPaperPageRequestVM;
import com.sias.commons.mapper.BaseMapper;
import com.sias.admin.domain.ExamPaper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author 123
* @description 针对表【exam_paper】的数据库操作Mapper
* @createDate 2023-03-31 14:01:52
* @Entity com.sias.admin.domain.ExamPaper
*/
@Mapper
public interface ExamPaperMapper extends BaseMapper<ExamPaper> {


  List<ExamPaper> page(ExamPaperPageRequestVM requestVM);

  List<ExamPaper> selectPaper();

}
