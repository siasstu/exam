package com.sias.commons.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;


@Data
public class SysOperationLog implements Serializable {

    private Integer id;
    private Date createTime;
    private String location;
    private String ip;
    private String operation;
    private String url;
    private String username;
    private String method;
    private String title;

    private static final long serialVersionUID = 1L;
}