package com.game.user.task.entity;

import com.db.AbstractEntity;
import com.game.user.task.model.TaskInfo;
import com.game.util.JsonUtils;
import org.hibernate.annotations.Table;

import javax.persistence.*;

/**
 * @Author：xuxin
 * @Date: 2019/7/24 22:16
 */
@Entity(name = "task")
@Table(appliesTo = "task", comment = "任务数据")
public class TaskEnt extends AbstractEntity<String> {
    /**
     * 账号Id
     */
    @Id
    @Column(columnDefinition = "varchar(255) character set utf8 collate utf8_bin comment '账号Id'", nullable = false)
    private String accountId;

    @Transient
    private TaskInfo taskInfo;

    public static TaskEnt valueOf() {
        TaskEnt taskEnt = new TaskEnt();
        taskEnt.setTaskInfo(TaskInfo.valueOf());
        return taskEnt;
    }


    @Lob
    @Column(columnDefinition = "Blob comment '任务数据'", nullable = false)
    private byte[] taskData;

    @Override
    public void doSerialize() {
        taskData = JsonUtils.object2Bytes(taskInfo);
    }

    @Override
    public void doDeserialize() {
        taskInfo = JsonUtils.bytes2Object(taskData, TaskInfo.class);
    }

    @Override
    public String getId() {
        return accountId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public TaskInfo getTaskInfo() {
        return taskInfo;
    }

    public void setTaskInfo(TaskInfo taskInfo) {
        this.taskInfo = taskInfo;
    }

    public byte[] getTaskData() {
        return taskData;
    }

    public void setTaskData(byte[] taskData) {
        this.taskData = taskData;
    }
}
