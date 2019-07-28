package com.game.user.task.service;

import com.game.user.task.resource.TaskLineResource;
import com.game.user.task.resource.TaskResource;
import com.resource.core.StorageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author：xuxin
 * @Date: 2019/7/24 22:56
 */
@Component
public class TaskManager {
    @Autowired
    private StorageManager storageManager;

    public TaskResource getTaskResource(int taskId) {
        return storageManager.getResource(taskId, TaskResource.class);
    }

    public TaskLineResource getTaskLineResource(int taskType) {
        return storageManager.getResource(taskType, TaskLineResource.class);
    }



}
