package com.game.base.core.service;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author：xuxin
 * @Date: 2019/6/5 10:34
 */
@Component
public class IdentifyService {
    private static final AtomicLong index = new AtomicLong(10000);

    public long getNextIdentify(){
        return index.incrementAndGet();
    }
}
