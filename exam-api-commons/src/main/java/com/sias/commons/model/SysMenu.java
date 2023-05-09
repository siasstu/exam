package com.sias.commons.model;

import com.sias.commons.base.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 吴文杰
 * @version 1.0
 * @createTime 2023-03-12 14:47:58
 */
@Data
public class SysMenu extends BaseEntity implements Serializable {
  /**
   * 菜单名称
   */
  private String name;

  /**
   * 菜单图标
   */
  private String icon;

  /**
   * 父菜单ID
   */
  private Long parentId;

  /**
   * 显示顺序
   */
  private Integer orderNum;

  /**
   * 路由地址
   */
  private String path;

  /**
   * 组件路径
   */
  private String component;

  /**
   * 菜单类型（M目录 C菜单 F按钮）
   */
  private String menuType;

  /**
   * 权限标识
   */
  private String perms;

  private List<SysMenu> children = new ArrayList<>();

  private static final long serialVersionUID = 1L;
}
