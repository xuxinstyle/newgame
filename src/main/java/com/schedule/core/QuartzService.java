package com.schedule.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author：xuxin
 * @Date: 2019/6/19 19:14
 */
@Component
public class QuartzService {
    @Autowired
    private QuartzManager quartzManager;

    public void start(){
        quartzManager.startJobs();
    }
}
