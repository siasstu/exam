package com.sias.admin.mapper;

import com.sias.admin.domain.Subject;
import com.sias.admin.vm.subject.SubjectSelectVM;
import com.sias.admin.vm.subject.SubjectVM;
import com.sias.commons.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 123
* @description 针对表【subject】的数据库操作Mapper
* @createDate 2023-03-13 12:08:39
* @Entity generator.domain.Subject
*/
@Mapper
public interface SubjectMapper extends BaseMapper<Subject> {

  List<Subject> page(SubjectSelectVM requestVM);

  List<Subject> selectByIds(@Param("ids") List<Long> ids);

  List<SubjectVM> list(String username);
}
