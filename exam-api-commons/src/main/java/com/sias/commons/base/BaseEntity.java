package com.sias.commons.base;

import com.alibaba.excel.annotation.ExcelIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 吴文杰
 * @version 1.0
 * @createTime 2023-03-10 12:04:54
 */
@Data
public class BaseEntity implements Serializable {

  @ExcelIgnore
  private Long id;

  /**
   * 创建日期
   */
  @ExcelIgnore
  private Date createTime;

  /**
   * 更新日期
   */
  @ExcelIgnore
  private Date updateTime;

  /**
   * 备注
   */
  @ExcelIgnore
  private String remark;
  @ExcelIgnore
  private Boolean deleted;
}