package com.sias.commons.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 吴文杰
 * @version 1.0
 * @createTime 2023-03-12 14:48:34
 */
@Data
public class SysUserRole implements Serializable {
  /**
   * 用户角色主键ID
   */
  private Long id;

  /**
   * 用户ID
   */
  private Long userId;

  /**
   * 角色ID
   */
  private Long roleId;

  private static final long serialVersionUID = 1L;
}
