package com.game.base.account.mapper;

import com.game.base.account.entity.AccountEnt;
import com.game.base.account.mapper.AccountExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;


public interface AccountMapper {
    long countByExample(AccountExample example);

    int deleteByExample(AccountExample example);

    int deleteByPrimaryKey(String accountid);

    int insert(AccountEnt record);

    int insertSelective(AccountEnt record);

    List<AccountEnt> selectByExampleWithBLOBs(AccountExample example);

    List<AccountEnt> selectByExample(AccountExample example);

    AccountEnt selectByPrimaryKey(String accountid);

    int updateByExampleSelective(@Param("record") AccountEnt record, @Param("example") AccountExample example);

    int updateByExampleWithBLOBs(@Param("record") AccountEnt record, @Param("example") AccountExample example);

    int updateByExample(@Param("record") AccountEnt record, @Param("example") AccountExample example);

    int updateByPrimaryKeySelective(AccountEnt record);

    int updateByPrimaryKeyWithBLOBs(AccountEnt record);

    int updateByPrimaryKey(AccountEnt record);
}