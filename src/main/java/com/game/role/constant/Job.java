package com.game.role.constant;

/**
 * @Author：xuxin
 * @Date: 2019/6/3 15:07
 */
public enum Job {
    WARRIOR(1,"战士"),
    MAGICIAN(2,"法师"),
    TAOIST(3,"道士"),
    ;
    private int jobType;
    private String name;
    private Job(int type, String name){
        this.jobType = type;
        this.name = name;
    }
}
