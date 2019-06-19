package com.game.base.executor.scene;

import com.game.base.executor.scene.Impl.AbstractSceneCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author：xuxin
 * @Date: 2019/6/11 17:10
 */
@Component
public class SceneExecutorServiceImpl implements SceneExecutorService {

    @Autowired
    public SceneThreadPoolExecutor sceneThreadPoolExecutor;
    @Override
    public void init() {
        sceneThreadPoolExecutor.start();
    }

    @Override
    public void submit(AbstractSceneCommand commond) {
        sceneThreadPoolExecutor.addTask(commond);
    }
}
