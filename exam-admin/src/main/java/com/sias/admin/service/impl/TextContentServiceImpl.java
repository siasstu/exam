package com.sias.admin.service.impl;

import com.sias.commons.service.impl.BaseServiceImpl;
import com.sias.admin.domain.TextContent;
import com.sias.admin.mapper.TextContentMapper;
import com.sias.admin.service.TextContentService;
import org.springframework.stereotype.Service;

/**
* @author 123
* @description 针对表【text_content】的数据库操作Service实现
* @createDate 2023-03-31 13:14:23
*/
@Service
public class TextContentServiceImpl extends BaseServiceImpl<TextContent>
implements TextContentService{

  private final TextContentMapper textContentMapper;

  public TextContentServiceImpl(TextContentMapper textContentMapper) {
    super(textContentMapper);
    this.textContentMapper = textContentMapper;
  }
}
