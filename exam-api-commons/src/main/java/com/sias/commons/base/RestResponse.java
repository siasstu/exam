package com.sias.commons.base;

import com.sias.commons.enums.SystemCode;
import lombok.Data;

/**
 * @author 吴文杰
 * @version 1.0
 * @createTime 2023-03-12 14:01:33
 */
@Data
public class RestResponse<T> {
  private int code;
  private String message;
  private T data;

  public RestResponse(int code, String message) {
    this.code = code;
    this.message = message;
  }

  public RestResponse(SystemCode systemCode, T data) {
    this.code = systemCode.code;
    this.message = systemCode.message;
    this.data = data;
  }
  public RestResponse(int code, String message, T data) {
    this.code = code;
    this.message = message;
    this.data = data;
  }

  public static RestResponse fail(Integer code, String msg) {
    return new RestResponse<>(code, msg);
  }

  public static RestResponse fail(SystemCode systemCode) {
    return new RestResponse<>(systemCode.code, systemCode.message);
  }

  public static RestResponse ok() {
    SystemCode systemCode = SystemCode.OK;
    return new RestResponse<>(systemCode.getCode(), systemCode.getMessage());
  }

  public static <F> RestResponse<F> ok(F data) {
    SystemCode systemCode = SystemCode.OK;
    return new RestResponse<>(systemCode.getCode(), systemCode.getMessage(), data);
  }
}
