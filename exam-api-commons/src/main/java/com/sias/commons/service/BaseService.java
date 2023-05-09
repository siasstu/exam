package com.sias.commons.service;

/**
 * @author 吴文杰
 * @version 1.0
 * @createTime 2023-03-09 18:16:53
 */
public interface BaseService<T> {

  int deleteById(Integer id);

  int insert(T record);

  int insertByFilter(T record);

  T selectById(Integer id);

  int updateByIdFilter(T record);

  int updateById(T record);
}