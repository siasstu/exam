package com.sias.admin.mapper;

import com.sias.commons.mapper.BaseMapper;
import com.sias.admin.domain.Knows;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 123
* @description 针对表【knows】的数据库操作Mapper
* @createDate 2023-03-22 16:18:56
* @Entity com.sias.admin.domain.Knows
*/
@Mapper
public interface KnowsMapper extends BaseMapper<Knows> {


  List<Knows> selectBySectionId(@Param("ids") List<Integer> sectionIds);
}
