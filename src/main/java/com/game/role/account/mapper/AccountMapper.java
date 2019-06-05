package com.game.role.account.mapper;

import com.game.role.account.entity.AccountEnt;

public interface AccountMapper {
    /**
     * 新增account信息
     * @param record
     * @return
     */
    int insertAccountEnt(AccountEnt record);

    /**
     * 查询account信息
     * @param accountId
     * @return
     */
    AccountEnt selectAccountEnt(String accountId);

    /**
     * 更新Account信息
     * @param record
     * @return
     */
    int updateAccountEnt(AccountEnt record);
}