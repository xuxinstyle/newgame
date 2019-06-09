package com.socket.dispatcher.executor;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * TODO: 这里还没用上，后续加了线程池再用
 * @Author：xuxin
 * @Date: 2019/4/29 15:16
 */
public class IdentifyTasksQueue implements ITaskQueue {
    private static int index = 0;
    private final int indentfy = ++index;
    private final Queue<Runnable> queue = new LinkedList<>();
    private AtomicBoolean empty = new AtomicBoolean(true);

    @Override
    public int getIdentify() {
        return indentfy;
    }

    @Override
    public boolean addTask(Runnable r) {
        synchronized (queue){
            queue.add(r);
        }
        if(empty.compareAndSet(Boolean.TRUE, Boolean.FALSE)){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void clear() {
        synchronized (queue){
            queue.clear();
        }
    }

    @Override
    public void run() {
        Runnable r = null;
        synchronized (queue){
            r = queue.poll();
            if(r == null){
                empty.compareAndSet(Boolean.FALSE, Boolean.TRUE);
            }
        }
        while (r != null){
            r.run();
            synchronized (queue){
                r = queue.poll();
                if(r == null){
                    empty.compareAndSet(Boolean.FALSE, Boolean.TRUE);
                }
            }
        }

    }
}
