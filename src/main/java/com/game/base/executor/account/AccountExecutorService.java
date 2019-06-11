package com.game.base.executor.account;

import com.game.base.executor.account.Impl.AbstractAccountCommond;

/**
 * @Author：xuxin
 * @Date: 2019/6/11 17:11
 */
public interface AccountExecutorService {
    /**
     * 开启线程
     */
    void init();

    public void submit(AbstractAccountCommond commond);

}
