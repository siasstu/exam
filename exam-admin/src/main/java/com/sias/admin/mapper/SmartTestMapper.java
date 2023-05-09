package com.sias.admin.mapper;

import com.sias.commons.mapper.BaseMapper;
import com.sias.admin.domain.SmartTest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author 123
* @description 针对表【smart_test】的数据库操作Mapper
* @createDate 2023-04-01 17:21:35
* @Entity com.sias.admin.domain.SmartTest
*/
@Mapper
public interface SmartTestMapper extends BaseMapper<SmartTest> {


  List select();

}
