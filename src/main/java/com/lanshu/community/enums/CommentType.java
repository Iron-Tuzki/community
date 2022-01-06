package com.lanshu.community.enums;

public enum CommentType {
    /*type 1 为问题，2 为回复*/
    QUESTION(1),
    COMMENT(2);

    private Integer type;
    //构造方法
    CommentType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public static boolean contain(Integer type) {
        //遍历枚举
        for (CommentType commentType : CommentType.values()) {
            if (type.equals(commentType.getType())){
                return true; //存在该类型
            }
        }
        return false; //不存在
    }
}
