package com.sias.commons.constant;

/**
 * @author 吴文杰
 * @version 1.0
 * @createTime 2023-03-12 13:59:14
 */
public class Constant {
  /**
   * token
   */
  public static final int JWT_ERRCODE_EXPIRE = 4001;			// Token过期
  public static final int JWT_ERRCODE_FAIL = 4002;			// 验证不通过
  public static final String DEFAULT_PASSWORD = "123456";			// 验证不通过

  /**
   * JWT
   */
  public static final String JWT_SECERT = "8677df7fc3a34e26a61c034d5ec8245d";			// 密匙
  public static final long JWT_TTL = 24*60 * 60 * 1000;									// token有效时间
}
