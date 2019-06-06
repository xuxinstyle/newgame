package com.game.scence.packet;

import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/6/6 10:02
 */
public class SM_ShowAllAccountInfo {
    /**
     * <账号Id，昵称>
     */
    private Map<String , String> accountMap;

    public Map<String, String> getAccountMap() {
        return accountMap;
    }

    public void setAccountMap(Map<String, String> accountMap) {
        this.accountMap = accountMap;
    }
}
