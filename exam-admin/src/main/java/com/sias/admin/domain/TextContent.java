package com.sias.admin.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName text_content
 */
@Data
public class TextContent implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 内容
     */
    private String content;

    /**
     * 创建时间
     */
    private Date createTime;
    public TextContent() {
    }

    public TextContent(String content, Date createTime) {
        this.content = content;
        this.createTime = createTime;
    }
    private static final long serialVersionUID = 1L;
}