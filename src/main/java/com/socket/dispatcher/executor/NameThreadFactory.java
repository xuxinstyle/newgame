package com.socket.dispatcher.executor;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author：xuxin
 * @Date: 2019/6/11 16:32
 */
public class NameThreadFactory implements ThreadFactory {
    private final String name;
    private final AtomicInteger index = new AtomicInteger();
    public NameThreadFactory(String name){
        this.name = name;
    }
    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, name+ "-"+index.incrementAndGet());
    }
}
