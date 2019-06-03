package com.game.base.player.model;

import com.game.base.gameObject.GameObject;
import org.springframework.stereotype.Service;

import javax.persistence.Transient;

@Service
public class Player extends GameObject {

    // 账号Id
    private String accountId;
    // 角色姓名
    private String playerName;

    @Transient
    private volatile Player player;

    @Override
    public String gatName() {
        return null;
    }
}
