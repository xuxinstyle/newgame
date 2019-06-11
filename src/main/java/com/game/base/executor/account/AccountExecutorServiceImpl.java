package com.game.base.executor.account;

import com.game.base.executor.account.Impl.AbstractAccountCommond;
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
    public void submit(AbstractAccountCommond commond) {
        accountThreadPoolExecutor.addTask(commond);
    }
}
