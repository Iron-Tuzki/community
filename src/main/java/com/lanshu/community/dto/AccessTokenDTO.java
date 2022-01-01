package com.lanshu.community.dto;

import lombok.Data;

@Data
public class AccessTokenDTO {
    //使用 code 获取accesstoken 需要四个参数，将其封装为一个类
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
}
