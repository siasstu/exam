package com.sias.commons.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 吴文杰
 * @version 1.0
 * @createTime 2023-03-12 14:48:15
 */
@Data
public class SysRoleMenu implements Serializable {
  /**
   * 角色菜单主键ID
   */
  private Long id;

  /**
   * 角色ID
   */
  private Long roleId;

  /**
   * 菜单ID
   */
  private Long menuId;

  private static final long serialVersionUID = 1L;
}
