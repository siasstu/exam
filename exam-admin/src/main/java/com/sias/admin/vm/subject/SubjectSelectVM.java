package com.sias.admin.vm.subject;

import com.sias.commons.base.BasePage;
import lombok.Data;

/**
 * @author 吴文杰
 * @version 1.0
 * @createTime 2023-03-24 17:30:13
 */
@Data
public class SubjectSelectVM extends BasePage {
  private String name;
  private Integer semesterId;
  private String username;
}
