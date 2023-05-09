package com.sias.commons.enums;

/**
 * @author 吴文杰
 * @version 1.0
 * @createTime 2023-03-12 14:01:33
 */
public enum SystemCode {
  OK(200, "成功"),

  UNAUTHORIZED(401, "用户未登录"),

  AuthError(402, "用户名或密码错误"),

  AuthExpired(406, "用户信息过期，请重新登录"),

  InnerError(500, "系统内部错误"),

  ParameterValidError(501, "参数验证错误"),

  UserCountLock(503, "用户账号被封禁"),

  AccessDenied(502, "您的权限不足"),

  FORBIDDEN(1, "禁用"),

  NORMAL(0, "正常"),


  JWT_ERRCODE_EXPIRE(4001, "Token过期"),

  JWT_ERRCODE_FAIl(4002, "Token验证不通过"),

  JWT_ERRCODE_NULL(4003, "请先登录！！");

  public int code;

  public String message;

  SystemCode(int code, String message) {
    this.code = code;
    this.message = message;
  }

  public int getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}