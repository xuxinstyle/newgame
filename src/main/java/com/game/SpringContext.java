package com.game;

import com.game.base.account.service.AccountService;
import com.game.register.service.RegisterService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Author：xuxin
 * @Date: 2019/4/29 11:35
 */
@Component
public class SpringContext implements ApplicationContextAware {

    @Autowired
    public ApplicationContext applicationContext;

    public static SpringContext instance;

    @PostConstruct
    private final void init(){
        instance = this;
    }
    @Autowired
    public ServerConfigValue serverConfigValue;

    @Autowired
    private RegisterService registerService;
    @Autowired
    private AccountService accountService;


    public static RegisterService getRegisterService(){
        return instance.registerService;
    }

    public static ServerConfigValue getServerConfigValue(){
        return instance.serverConfigValue;
    }

    public static AccountService getAccountService(){
        return instance.accountService;
    }

    @Override
    public void setApplicationContext(ApplicationContext contex) throws BeansException {
        this.applicationContext = contex;
    }
}
