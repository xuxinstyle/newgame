package com.game.world.rank.model;

import com.game.SpringContext;
import com.game.role.player.entity.PlayerEnt;
import com.game.role.player.model.Player;
import com.game.util.CommonUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * 战力排行榜
 *
 * @Author：xuxin
 * @Date: 2019/7/31 16:42
 */
public class BattleScoreRank {
    /**
     * 排行榜显示数量
     */
    private int num;
    /**
     * 排行榜默认缓存容量
     */
    public final static int DEFAULT_CAPACITY = 500;
    /**
     * 排行榜缓存容量
     */
    private int capacity;

    public BattleScoreRank(int capacity) {
        this.capacity = capacity;
        this.rank = new ConcurrentSkipListMap<>();
        cacheRankInfo = new ConcurrentHashMap<>();
        num= CommonUtil.RANK_MAX_NUM;
    }

    public BattleScoreRank() {
        this(DEFAULT_CAPACITY);
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
        if(isFull()){
            Map.Entry<PlayerBattleScoreRankInfo, Long> entry = rank.pollLastEntry();
            cacheRankInfo.remove(entry.getValue());
        }
    }

    boolean isFull(){
        return getRank().size()>this.capacity;
    }

    public Set<PlayerBattleScoreRankInfo> getInRankInfo(){
        Set<PlayerBattleScoreRankInfo> inRankInfos = new TreeSet<>();
        Iterator<PlayerBattleScoreRankInfo> iterator = rank.keySet().iterator();
        int i = 0;
        while(iterator.hasNext()&& i<this.num){
            inRankInfos.add(iterator.next());
            i++;
        }
        return inRankInfos;
    }
    /**
     * 更新指定玩家的排名
     *
     * @param player
     */
    public void updateRandByPlayer(Player player) {
        PlayerBattleScoreRankInfo rankInfo = cacheRankInfo.get(player.getObjectId());
        if (rankInfo == null) {
            return;
        }
        rank.remove(rankInfo);
        rankInfo.setBattleScore(player.getBattleScore());
        rank.put(rankInfo, rankInfo.getPlayerId());
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

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
