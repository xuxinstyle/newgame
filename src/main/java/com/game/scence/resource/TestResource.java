package com.game.scence.resource;

import com.resource.anno.Resource;
import org.springframework.stereotype.Component;


/**
 * @Author：xuxin
 * @Date: 2019/6/4 15:02
 */
@Component
@Resource
public class TestResource {
    public String id;
    public String username;
    public String passward;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassward() {
        return passward;
    }

    public void setPassward(String passward) {
        this.passward = passward;
    }

    @Override
    public String toString() {
        return "TestResource{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", passward='" + passward + '\'' +
                '}';
    }
}
