package com.sias.admin.mapper;

import com.sias.admin.domain.ExamGrade;
import com.sias.admin.vm.exam.ExamGradePageRequestVM;
import com.sias.commons.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 123
* @description 针对表【exam_grade】的数据库操作Mapper
* @createDate 2023-03-31 14:35:38
* @Entity com.sias.admin.domain.ExamGrade
*/
@Mapper
public interface ExamGradeMapper extends BaseMapper<ExamGrade> {

  List<ExamGrade> page(@Param("request") ExamGradePageRequestVM requestVM);

  List<ExamGrade> selecetByGradeId(@Param("id") Integer id);

  List<ExamGrade> selecetByGradeIds(@Param("ids") List<Integer> gradeIds);

  // List<KeyValue> selectCountByDate(Date startTime, Date endTime);
}
