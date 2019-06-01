package com.game.base.account.service;

import com.game.base.account.entity.AccountEnt;

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
     * 删除账号
     */
    void drop(String username);
    /**
     * 修改账号
     */
    void update(String username, String passward);
    /**
     * 查询账号
     */
    List<AccountEnt> select(String username);
}
