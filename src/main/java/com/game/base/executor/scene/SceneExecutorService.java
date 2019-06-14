package com.game.base.executor.scene;

import com.game.base.executor.account.Impl.AbstractAccountCommand;
import com.game.base.executor.scene.Impl.AbstractSceneCommand;

/**
 * @Author：xuxin
 * @Date: 2019/6/11 17:11
 */
public interface SceneExecutorService {
    /**
     * 开启线程
     */
    void init();

    /**
     * 将command抛到对应的线程中执行
     * @param commond
     */
    public void submit(AbstractSceneCommand commond);

}
