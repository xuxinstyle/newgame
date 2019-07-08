package com.game.scence.fight.model;

import com.game.role.player.model.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/7/4 9:13
 */
public class FightAccount {

    private String accountId;
    /**
     * 成员战斗单元信息
     */
    private Map<Long, CreatureUnit> creatureUnitMap = new HashMap<>();

    public static FightAccount valueOf(Player player){
        FightAccount fightAccount = new FightAccount();
        PlayerUnit playerUnit = PlayerUnit.valueOf(player);
        fightAccount.setAccountId(player.getAccountId());
        Map<Long, CreatureUnit> creatureUnitMap = new HashMap<>();
        creatureUnitMap.put(playerUnit.getId(),playerUnit);
        fightAccount.setCreatureUnitMap(creatureUnitMap);
        return fightAccount;
    }
    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Map<Long, CreatureUnit> getCreatureUnitMap() {
        return creatureUnitMap;
    }

    public void setCreatureUnitMap(Map<Long, CreatureUnit> creatureUnitMap) {
        this.creatureUnitMap = creatureUnitMap;
    }
}
