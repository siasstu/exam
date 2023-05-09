package com.sias.commons.model;

import com.sias.commons.base.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 吴文杰
 * @version 1.0
 * @createTime 2023-03-12 14:48:07
 */
@Data
public class SysRole extends BaseEntity implements Serializable {
  /**
   * 角色名称
   */
  private String name;

  /**
   * 角色权限字符串
   */
  private String code;


  private static final long serialVersionUID = 1L;
}
