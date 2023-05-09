package com.sias.admin.mapper;

import com.sias.commons.mapper.BaseMapper;
import com.sias.admin.domain.ClassStu;
import com.sias.commons.model.SysUser;
import com.sias.system.VM.UserSelectRequestVm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 123
* @description 针对表【class_stu】的数据库操作Mapper
* @createDate 2023-03-24 18:15:17
* @Entity com.sias.admin.domain.ClassStu
*/
@Mapper
public interface ClassStuMapper extends BaseMapper<ClassStu> {


  List<ClassStu> selectByClassId(Long id);

  void deleteByUsernameAndClassId(@Param("username") String username,
                                  @Param("classId") Integer classId);

  void addStuToClass(@Param("username") String username,
                     @Param("classId") Integer classId);

  List<SysUser> selectStudentsByClassId(UserSelectRequestVm requestVm);
}
