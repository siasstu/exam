package com.sias.admin.vm.subject;

import lombok.Data;

import java.util.Date;

/**
 * @author 吴文杰
 * @version 1.0
 * @createTime 2023-03-24 16:51:53
 */
@Data
public class SubjectVM {

  private Integer id;

  /**
   * 课程名称
   */
  private String name;

  /**
   * 创建人
   */
  private String createUser;

  private String semesterName;

  /**
   * 创建日期
   */
  private Date createTime;

  /**
   * 备注
   */
  private String remark;

}
