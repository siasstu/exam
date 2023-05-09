package com.sias.commons.model;

import io.jsonwebtoken.Claims;
import lombok.Data;

/**
 * @author 吴文杰
 * @version 1.0
 * @createTime 2023-03-12 14:15:19
 */
@Data
public class CheckResult {

  private int errCode;

  private boolean success;

  private Claims claims;

}
