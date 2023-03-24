package com.neu.nodulesystem.config.bean;


import lombok.Data;

@Data
public class MinioPro {
    /**
     * 端点
     */
    private String url;
    /**
     * 用户名
     */
    private String accesskey;
    /**
     * 密码
     */
    private String secretKey;

    /**
     * 桶名称
     */
    private String bucketName;
}
