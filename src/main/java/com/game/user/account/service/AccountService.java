package com.game.user.account.service;

import com.game.user.account.entity.AccountEnt;
import com.socket.core.session.TSession;

/**
 * @Author：xuxin
 * @Date: 2019/5/29 19:31
 */
public interface AccountService {
    /**
     * 添加账号
     * @param username
     * @param passward
     */
    void insert(String username, String passward);


    /**
     * 获取账号实体
     * @param accountId
     * @return
     */
    AccountEnt getAccountEnt(String accountId);

    /**
     * 创建角色
     * @param nickName
     * @param career
     * @param account
     */
    void createFirstPlayer(TSession session, String nickName, int type, String accountId);

    /**
     * 保存修改
     * @param accountEnt
     */
    void save(AccountEnt accountEnt);
}
