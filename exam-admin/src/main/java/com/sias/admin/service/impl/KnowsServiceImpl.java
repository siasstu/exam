package com.sias.admin.service.impl;

import com.sias.commons.service.impl.BaseServiceImpl;
import com.sias.admin.domain.Knows;
import com.sias.admin.mapper.KnowsMapper;
import com.sias.admin.service.KnowsService;
import org.springframework.stereotype.Service;

/**
* @author 123
* @description 针对表【knows】的数据库操作Service实现
* @createDate 2023-03-22 16:18:56
*/
@Service
public class KnowsServiceImpl extends BaseServiceImpl<Knows>
implements KnowsService{

  private final KnowsMapper knowsMapper;
  public KnowsServiceImpl(KnowsMapper knowsMapper) {
    super(knowsMapper);
    this.knowsMapper = knowsMapper;
  }
}
