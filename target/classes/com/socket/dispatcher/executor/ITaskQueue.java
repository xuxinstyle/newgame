package com.socket.dispatcher.executor;

/**
 * @Author：xuxin
 * @Date: 2019/4/29 15:14
 */
// TODO: 这里还没用上，后续加了线程池再用
public interface ITaskQueue extends Runnable {
    int getIdentify();

    boolean addTask(Runnable r);

    void clear();
}
