package com.game.world.rank.model;

import com.game.SpringContext;
import com.game.role.player.entity.PlayerEnt;
import com.game.role.player.model.Player;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * 战力排行榜
 *
 * @Author：xuxin
 * @Date: 2019/7/31 16:42
 */
public class BattleScoreRank {

    public final static int DEFAULT_NUM = 100;

    public int capacity;

    public BattleScoreRank(int capacity) {
        this.capacity = capacity;
        this.rank = new ConcurrentSkipListMap<>();
        cacheRankInfo = new ConcurrentHashMap<>();
    }

    public BattleScoreRank() {
        this(DEFAULT_NUM);
    }

    /**
     * 排行榜缓存信息 为了方便查找 在每次更新玩家数据的时候从这个地方查找到玩家的排行榜数据 再去更新玩家的排行
     */
    private Map<Long, PlayerBattleScoreRankInfo> cacheRankInfo;

    /**
     * 排行榜
     */
    private ConcurrentSkipListMap<PlayerBattleScoreRankInfo, Long> rank;

    public void init() {
        List<PlayerEnt> playerAll = SpringContext.getPlayerSerivce().getPlayerAll();
        for (PlayerEnt playerEnt : playerAll) {

            PlayerBattleScoreRankInfo playerBattleScoreRankInfo = new PlayerBattleScoreRankInfo();
            Player player = playerEnt.getPlayer();
            playerBattleScoreRankInfo.setBattleScore(player.getBattleScore());
            playerBattleScoreRankInfo.setLevel(player.getLevel());
            playerBattleScoreRankInfo.setAccountId(player.getAccountId());
            playerBattleScoreRankInfo.setPlayerId(player.getObjectId());
            putNewRankInfo(playerBattleScoreRankInfo);
        }
    }

    /**
     * 向排行榜增加数据
     *
     * @param rankInfo
     */
    public void putNewRankInfo(PlayerBattleScoreRankInfo rankInfo) {
        rank.put(rankInfo, rankInfo.getPlayerId());
        cacheRankInfo.put(rankInfo.getPlayerId(), rankInfo);
    }

    /**
     * 更新指定玩家的排名
     *
     * @param rankInfo
     * @param player
     */
    public void updateRand(PlayerBattleScoreRankInfo rankInfo, Player player) {
        if (rankInfo == null) {
            return;
        }
        rank.remove(rankInfo);
        rankInfo.setBattleScore(player.getBattleScore());
        rank.put(rankInfo, rankInfo.getPlayerId());
    }

    public void updateRandByPlayer(Player player) {
        PlayerBattleScoreRankInfo playerBattleScoreRankInfo = cacheRankInfo.get(player.getObjectId());

        updateRand(playerBattleScoreRankInfo, player);
    }

    /**
     * 获取最后一名玩家数据
     *
     * @return
     */
    public PlayerBattleScoreRankInfo getLastRankInfo() {
        return rank.lastKey();
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Map<Long, PlayerBattleScoreRankInfo> getCacheRankInfo() {
        return cacheRankInfo;
    }

    public void setCacheRankInfo(Map<Long, PlayerBattleScoreRankInfo> cacheRankInfo) {
        this.cacheRankInfo = cacheRankInfo;
    }

    public ConcurrentSkipListMap<PlayerBattleScoreRankInfo, Long> getRank() {
        return rank;
    }

    public void setRank(ConcurrentSkipListMap<PlayerBattleScoreRankInfo, Long> rank) {
        this.rank = rank;
    }
}
