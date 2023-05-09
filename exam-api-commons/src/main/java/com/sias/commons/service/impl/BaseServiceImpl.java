package com.sias.commons.service.impl;

import com.sias.commons.mapper.BaseMapper;
import com.sias.commons.service.BaseService;
import com.sias.commons.utils.ModelMapperSingle;
import org.modelmapper.ModelMapper;

/**
 * @author 吴文杰
 * @version 1.0
 * @createTime 2023-03-09 18:17:14
 */
public abstract class BaseServiceImpl<T> implements BaseService<T> {

  protected final static ModelMapper modelMapper = ModelMapperSingle.Instance();
  private final BaseMapper<T> baseMapper;

  public BaseServiceImpl(BaseMapper<T> baseMapper) {
    this.baseMapper = baseMapper;
  }

  @Override
  public int insert(T record) {
    return baseMapper.insert(record);
  }

  @Override
  public int insertByFilter(T record) {
    return baseMapper.insertSelective(record);
  }

  @Override
  public int deleteById(Integer id) {
    return baseMapper.deleteByPrimaryKey(id);
  }

  @Override
  public int updateByIdFilter(T record) {
    return baseMapper.updateByPrimaryKeySelective(record);
  }

  @Override
  public int updateById(T record) {
    return baseMapper.updateByPrimaryKey(record);
  }

  @Override
  public T selectById(Integer id) { return baseMapper.selectByPrimaryKey(id); }
}