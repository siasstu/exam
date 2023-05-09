package com.sias.admin.mapper;

import com.sias.admin.vm.ExamPaperAnswerPageRequestVM;
import com.sias.commons.mapper.BaseMapper;
import com.sias.admin.domain.ExamPaperAnswer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 123
* @description 针对表【exam_paper_answer】的数据库操作Mapper
* @createDate 2023-03-31 15:38:17
* @Entity generator.domain.ExamPaperAnswer
*/
@Mapper
public interface ExamPaperAnswerMapper extends BaseMapper<ExamPaperAnswer> {

  List<ExamPaperAnswer> page(ExamPaperAnswerPageRequestVM requestVM);

  List<ExamPaperAnswer> select(@Param("gradeId") Integer gradeId,@Param("examGradeId") Integer id);

  List<ExamPaperAnswer> selectByGradeId(Integer gradeId);

  ExamPaperAnswer selectByExamGradeIdAndIdcard(@Param("examGradeId") Integer id,@Param("idcard") String idcard);

  List<ExamPaperAnswer> selectByIdcard(String idcard);
}
