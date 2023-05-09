package com.sias.commons.mapper;

/**
 * @author 吴文杰
 * @version 1.0
 * @createTime 2023-03-12 14:20:01
 */
public interface BaseMapper<T> {

  int deleteByPrimaryKey(Integer id);

  int insert(T record);

  int insertSelective(T record);

  T selectByPrimaryKey(Integer id);

  int updateByPrimaryKeySelective(T record);

  int updateByPrimaryKey(T record);
}
