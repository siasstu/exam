package com.sias.commons.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName sys_login_log
 */
@Data
public class SysLoginLog implements Serializable {
    private Integer id;
    private Date createTime;
    private String username;
    private String ip;
    private String location;
    private String browser;
    private String os;
    private String info;
    private int status;

    private static final long serialVersionUID = 1L;
}