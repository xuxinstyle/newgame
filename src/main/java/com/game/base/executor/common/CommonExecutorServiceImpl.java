package com.game.base.executor.common;

import com.game.base.executor.common.impl.AbstractCommonCommand;
import com.game.base.executor.common.impl.AbstractCommonDelayCommand;
import com.game.base.executor.common.impl.AbstractCommonRateCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author：xuxin
 * @Date: 2019/6/12 14:05
 */
@Component
public class CommonExecutorServiceImpl implements CommonExecutorService {

    @Autowired
    private CommonExecutor commonExecutor;
    @Override
    public void init() {
        commonExecutor.start();
    }

    @Override
    public void submit(AbstractCommonCommand commond) {
        if(commond instanceof AbstractCommonRateCommand){
            commonExecutor.schedule((AbstractCommonRateCommand)commond);
        }else if(commond instanceof AbstractCommonDelayCommand){
            commonExecutor.schedule((AbstractCommonDelayCommand) commond);
        }else {
            commonExecutor.addTask(commond);
        }
    }

}
