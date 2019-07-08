package com.game.scence.npc.resource;

import com.resource.anno.LoadResource;

/**
 * @Author：xuxin
 * @Date: 2019/7/3 11:08
 */
@LoadResource
public class NpcResource {
    /**
     * 标识id
     */
    private int id;
    /**
     * npc名字
     */
    private String name;
    /**
     * x位置
     */
    private int px;
    /**
     * py
     */
    private int py;
    /**
     * npc所在的mapId
     */
    private int mapId;
    /**
     * 对话内容
     */
    private String chat;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPx() {
        return px;
    }

    public void setPx(int px) {
        this.px = px;
    }

    public int getPy() {
        return py;
    }

    public void setPy(int py) {
        this.py = py;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public String getChat() {
        return chat;
    }

    public void setChat(String chat) {
        this.chat = chat;
    }
}
