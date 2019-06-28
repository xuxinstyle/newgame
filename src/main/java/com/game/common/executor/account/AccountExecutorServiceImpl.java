package com.game.common.executor.account;

import com.game.common.executor.account.impl.AbstractAccountCommand;
import com.game.common.executor.account.impl.AbstractAccountDelayCommand;
import com.game.common.executor.account.impl.AbstractAccountRateCommand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author：xuxin
 * @Date: 2019/6/11 17:10
 */
@Component
public class AccountExecutorServiceImpl implements AccountExecutorService{

    @Autowired
    public AccountThreadPoolExecutor accountThreadPoolExecutor;
    @Override
    public void init() {
        accountThreadPoolExecutor.start();
    }

    @Override
    public void submit(AbstractAccountCommand commond) {
        if(commond instanceof AbstractAccountDelayCommand){
            AbstractAccountDelayCommand accountDelayCommond = (AbstractAccountDelayCommand) commond;
            accountThreadPoolExecutor.schedule(accountDelayCommond,accountDelayCommond.getDelay());
        }else if(commond instanceof AbstractAccountRateCommand){
            AbstractAccountRateCommand accountRateCommand = (AbstractAccountRateCommand) commond;
            accountThreadPoolExecutor.schedule(commond,accountRateCommand.getDelay(),accountRateCommand.getPeriod());
        }else {
            accountThreadPoolExecutor.addTask(commond);
        }
    }


}
