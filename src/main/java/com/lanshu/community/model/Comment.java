package com.lanshu.community.model;

import lombok.Data;

@Data
public class Comment {

    private Integer id;

    private Integer parentId;

    private Integer type;

    private Integer commentatorId;

    private Long gmtCreate;

    private Long gmtModified;

    private Integer likeCount;

    private String content;

}