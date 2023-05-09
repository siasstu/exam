package com.sias.admin.mapper;

import com.sias.admin.domain.KeyValue;
import com.sias.admin.domain.QuestionError;
import com.sias.commons.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 123
* @description 针对表【question_error】的数据库操作Mapper
* @createDate 2023-03-31 15:59:53
* @Entity generator.domain.QuestionError
*/
@Mapper
public interface QuestionErrorMapper extends BaseMapper<QuestionError> {

  List<QuestionError> page(@Param("idcard") String idcard,
                           @Param("courseId") Integer courseId);

  List<KeyValue> selectByExamGradeId(Integer examGradeId);

}
