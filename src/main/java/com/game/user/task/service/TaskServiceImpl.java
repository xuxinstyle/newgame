package com.game.user.task.service;

import com.db.EntityBuilder;
import com.db.cache.EntityCacheService;
import com.game.user.task.entity.TaskEnt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author：xuxin
 * @Date: 2019/7/24 22:55
 */
@Component
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskManager taskManager;
    @Autowired
    private EntityCacheService<String, TaskEnt> taskCacheService;

    public TaskEnt getOrCreateTask(String accountId) {
        return taskCacheService.findOrCreate(TaskEnt.class, accountId, new EntityBuilder<String, TaskEnt>() {
            @Override
            public TaskEnt newInstance(String id) {
                return TaskEnt.valueOf();
            }
        });
    }

}
