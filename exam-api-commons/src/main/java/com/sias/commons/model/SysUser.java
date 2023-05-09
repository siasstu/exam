package com.sias.commons.model;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.*;
import com.alibaba.excel.enums.BooleanEnum;
import com.alibaba.excel.enums.poi.HorizontalAlignmentEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sias.commons.base.BaseEntity;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author 吴文杰
 * @version 1.0
 * @createTime 2023-03-12 14:48:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@HeadRowHeight(15)
@HeadFontStyle(bold = BooleanEnum.FALSE,color = 0)
@ContentFontStyle(fontHeightInPoints = 10,bold = BooleanEnum.FALSE,color = 10)
@ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
public class SysUser extends BaseEntity implements Serializable {
  /**
   * 真实姓名
   */
  @ExcelProperty(value = "姓名",index = 0)
  @ColumnWidth(15)
  private String realName;

  /**
   * 用户名
   */
  @ExcelProperty(value = "学号",index = 1)
  @ColumnWidth(22)
  private String username;

  /**
   * 手机号码
   */
  @ExcelProperty(value = "手机号",index = 2)
  @ColumnWidth(22)
  private String phoneNumber;

  /**
   * 用户邮箱
   */
  @ExcelProperty(value = "邮箱",index = 3)
  @ColumnWidth(25)
  private String email;

  /**
   * 性别
   */
  @ExcelProperty(value = "性别",index = 4)
  @ColumnWidth(8)
  private String sex;

  /**
   * 出生日期
   */
  @ExcelProperty(value = "出身日期",index = 5)
  @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
  @DateTimeFormat("yyyy-MM-dd")
  @ColumnWidth(22)
  private Date birthDay;

  public SysUser(String username,  String email, String phoneNumber, String sex, Date birthDay, String realName,String className) {
    this.username = username;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.sex = sex;
    this.birthDay = birthDay;
    this.realName = realName;
    this.className = className;
  }

  /**
   * 班级名称
   */
  @ExcelProperty(value = "班级名称",index = 6)
  @ColumnWidth(22)
  private String className;

  /**
   * 密码
   */
  @ExcelIgnore
  private String password;

  /**
   * 用户头像
   */
  @ExcelIgnore
  private String avatar;



  /**
   * 帐号状态（0正常 1停用）
   */
  @ExcelIgnore
  private String status;

  /**
   * 所属角色
   */
  @ExcelIgnore
  private String roles;

  /**
   * 角色集合
   */
  @ExcelIgnore
  private List<SysRole> roleList;

  @ExcelIgnore
  private static final long serialVersionUID = 1L;
}
