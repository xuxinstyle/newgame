package com.game.base.account.service;

import com.game.base.account.entity.AccountEnt;
import com.game.base.account.mapper.AccountExample;
import com.game.base.account.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author：xuxin
 * @Date: 2019/5/29 19:31
 */
@Component
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public void insert(String username, String passward) {
        AccountEnt accountEnt = new AccountEnt();
        accountEnt.setAccountId(username);
        accountEnt.setPassward(passward);
        accountMapper.insert(accountEnt);
    }

    @Override
    public void drop(String username) {

    }

    @Override
    public void update(String username, String passward) {

    }

    @Override
    public List<AccountEnt> select(String username) {
        AccountExample example = new AccountExample();
        AccountExample.Criteria criteria = example.createCriteria();
        criteria.andAccountidEqualTo(username);
        List<AccountEnt> accountEnts = accountMapper.selectByExample(example);
        return accountEnts;
    }
}
