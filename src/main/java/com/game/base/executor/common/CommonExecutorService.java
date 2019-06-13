package com.game.base.executor.common;

import com.game.base.executor.common.Impl.AbstractCommonCommand;

/**
 * @Author：xuxin
 * @Date: 2019/6/12 14:05
 */
public interface CommonExecutorService {
    /**
     * 开启线程
     */
    void init();

    /**
     * 将command抛到对应的线程中执行
     * @param commond
     */
    public void submit(AbstractCommonCommand commond);
}
