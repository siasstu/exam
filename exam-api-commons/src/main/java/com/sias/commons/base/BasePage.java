package com.sias.commons.base;

import lombok.Data;

/**
 * @author 吴文杰
 * @version 1.0
 * @createTime 2023-03-11 19:15:14
 */
@Data
public class BasePage {
  private Integer pageIndex;
  private Integer pageSize;
}