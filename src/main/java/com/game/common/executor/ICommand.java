package com.game.common.executor;

/**
 * @Author：xuxin
 * @Date: 2019/6/11 17:16
 */
public interface ICommand{
    /**
     * 获取commond的key
     * @return
     */
    Object getKey();
    /**
     * 获取名字
     */
    String getName();
    /**
     * 执行任务逻辑
     */
    void active();

    /**
     * 获得执行任务线程的线程编号
     * @param poolsize
     * @return
     */
    int modIndex(int poolsize);

    /**
     * 是否被取消
     * @return
     */
    boolean isCanceled();

    /**
     * 取消定时任务的commond
     */
    void cancel();

    /**
     * 刷新状态
     */
    void refreshState();

}
