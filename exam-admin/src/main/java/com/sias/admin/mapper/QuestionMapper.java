package com.sias.admin.mapper;

import com.sias.admin.vm.question.QuestionPageRequestVM;
import com.sias.commons.mapper.BaseMapper;
import com.sias.admin.domain.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 123
* @description 针对表【question】的数据库操作Mapper
* @createDate 2023-03-31 12:57:41
* @Entity com.sias.admin.domain.Question
*/
@Mapper
public interface QuestionMapper extends BaseMapper<Question> {


  List<Question> page(QuestionPageRequestVM requestVM);

  List<Question> selectByIds(@Param("ids") List<Integer> questionIds);

  List<Question> selectByQuestionCourseId(Integer courseId);
}
