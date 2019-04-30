package com.game.base.player.model;

import com.game.base.gameobject.GameObject;
import org.springframework.stereotype.Service;

@Service
public class Player extends GameObject {
    //账号Id
    private String accountId;

    @Override
    public String gatName() {
        return null;
    }
}
