package com.lanshu.community.dto;

import lombok.Data;

@Data
public class GithubUser {
    private String name;
    private long id;
    private String bio;
    private String avatarUrl;   //FastJson可以自动把JSON中的下划线字段转为驼峰命名规则的属性
}
