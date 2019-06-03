package com.game.base.account.service;

import com.game.base.account.entity.AccountEnt;
import com.socket.core.session.TSession;

import java.util.List;

/**
 * @Author：xuxin
 * @Date: 2019/5/29 19:31
 */
public interface AccountService {
    /**
     * 添加账号
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
    void createPlayer(TSession session, String nickName, String career, String accountId);
    /**
     * 保存修改
     */
    void save(AccountEnt accountEnt);
}
