package com.game.role.player.entity;

import com.db.AbstractEntity;
import com.game.role.player.model.Player;
import com.socket.Utils.ProtoStuffUtil;
import org.hibernate.annotations.Table;

import javax.persistence.*;

/**
 * @Author：xuxin
 * @Date: 2019/4/28 18:13
 */
@Entity(name="player")
@Table(appliesTo = "player", comment = "唯一标识id")
public class PlayerEnt extends AbstractEntity<Long> {
    @Id
    @Column(columnDefinition = "bigint default 10000 comment '角色id'", nullable = false)
    private long playerId;

    @Column(columnDefinition = "varchar(255) character set utf8 collate utf8_bin comment '账号Id'",nullable = false)
    private String accountId;

    @Transient
    private Player player;
    @Lob
    @Column(columnDefinition = "blob comment '角色数据'")
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

    @Override
    public Long getId() {
        return playerId;
    }

    public byte[] getPlayerData() {
        return playerData;
    }

    public void setPlayerData(byte[] playerData) {
        this.playerData = playerData;
    }
}
