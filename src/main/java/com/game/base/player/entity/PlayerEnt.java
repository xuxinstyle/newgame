package com.game.base.player.entity;

import com.db.AbstractEntity;
import com.game.base.player.model.Player;
import com.socket.Utils.ProtoStuffUtil;

import javax.persistence.Transient;
import java.io.Serializable;

/**
 * @Author：xuxin
 * @Date: 2019/4/28 18:13
 */
public class PlayerEnt extends AbstractEntity<Long> {

    private long playerId;

    private String accountId;

    @Transient
    private Player player;

    private byte[] playerData;


    @Override
    public void doSerialize() {
        this.playerData = ProtoStuffUtil.serializer(player);
    }

    @Override
    public void doDeserialize() {
        this.player = ProtoStuffUtil.deserializer(playerData, Player.class);
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public byte[] getPlayerData() {
        return playerData;
    }

    public void setPlayerData(byte[] playerData) {
        this.playerData = playerData;
    }

    @Override
    public Long getId() {
        return playerId;
    }
}
