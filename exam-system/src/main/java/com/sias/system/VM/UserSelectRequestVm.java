package com.sias.system.VM;

import com.sias.commons.base.BasePage;
import lombok.Data;

/**
 * @author 吴文杰
 * @version 1.0
 * @createTime 2023-03-18 11:55:06
 */
@Data
public class UserSelectRequestVm extends BasePage {
  private String username;
  private String realName;
  private Integer classId;
  private Integer roleId;
}
