package com.game.base.executor.scene;

import com.game.base.executor.scene.impl.AbstractSceneCommand;
import com.game.base.executor.scene.impl.AbstractSceneDelayCommand;
import com.game.base.executor.scene.impl.AbstractSceneRateCommand;
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
        if (commond instanceof AbstractSceneRateCommand) {
            AbstractSceneRateCommand sceneRateCommand = (AbstractSceneRateCommand) commond;
            sceneThreadPoolExecutor.schedule(sceneRateCommand,sceneRateCommand.getDelay(), sceneRateCommand.getPeriod());
        } else if (commond instanceof AbstractSceneDelayCommand) {
            AbstractSceneDelayCommand sceneDelayCommand = (AbstractSceneDelayCommand) commond;
            sceneThreadPoolExecutor.schedule(sceneDelayCommand, sceneDelayCommand.getDelay());
        }else {
            sceneThreadPoolExecutor.addTask(commond);
        }
    }
}
