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
    public static Job valueOf(int type){
        Job[] values = Job.values();
        for (Job job :values) {
            if(job.jobType== type){
                return job;
            }
        }
        return null;
    }
    public int getJobType() {
        return jobType;
    }


    public String getName() {
        return name;
    }

    private Job(int type, String name){
        this.jobType = type;
        this.name = name;
    }
}
