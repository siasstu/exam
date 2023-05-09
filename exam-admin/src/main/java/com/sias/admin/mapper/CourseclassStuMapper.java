package com.sias.admin.mapper;

import com.sias.admin.vm.ExamPaperAnswerPageRequestVM;
import com.sias.commons.mapper.BaseMapper;
import com.sias.admin.domain.CourseclassStu;
import com.sias.commons.model.SysUser;
import com.sias.system.VM.UserSelectRequestVm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
* @author 123
* @description 针对表【courseclass_stu】的数据库操作Mapper
* @createDate 2023-03-30 22:17:40
* @Entity com.sias.admin.domain.CourseclassStu
*/
@Mapper
public interface CourseclassStuMapper extends BaseMapper<CourseclassStu> {


  List<CourseclassStu> selectByCourseClassId(Integer id);

  List<SysUser> selectStudentsByClassId(UserSelectRequestVm requestVm);

  void deleteByUsernameAndClassId(@Param("username") String username,
                                  @Param("classId") Integer classId);

  void addStuToClass(@Param("username") String username,
                     @Param("classId") Integer classId);

  void addStudents(@Param("id")Integer id,
                   @Param("usernames") List<String> usernames);

  List<CourseclassStu> selectByUsername(String username);
  List<CourseclassStu> pageSelectByNotCompleted(@Param("gradeId") Integer gradeId,@Param("ids") List<String> collect);

  List<CourseclassStu> pageSelect(ExamPaperAnswerPageRequestVM requestVM);

}
