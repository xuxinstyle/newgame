package com.game.scence.resource;

import com.resource.anno.Resource;
import org.springframework.stereotype.Component;

/**
 * @Author：xuxin
 * @Date: 2019/6/4 20:47
 */
@Component
@Resource
public class MapResource {
    // 这里的属性必须为public的权限
    // 地图id
    public int id;
    //地图实际形状
    public String context;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
