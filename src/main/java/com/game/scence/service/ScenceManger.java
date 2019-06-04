package com.game.scence.service;

import com.game.scence.resource.TestResource;
import com.resource.Storage;
import com.resource.StorageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author：xuxin
 * @Date: 2019/6/4 15:10
 */
@Component
public class ScenceManger {

    public TestResource getResource(String id, Class<?> clz){
        return (TestResource)StorageManager.getResource(clz, id);
    }

}
