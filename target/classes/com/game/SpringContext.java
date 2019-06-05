package com.game;

import com.game.base.account.service.AccountService;
import com.game.base.core.service.IdentifyService;
import com.game.base.player.service.PlayerService;
import com.game.connect.service.ConnectService;
import com.game.login.service.LoginService;
import com.game.scence.service.ScenceService;
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
    @Autowired
    private LoginService loginService;
    @Autowired
    private ConnectService connectService;
    @Autowired
    private ScenceService scenceService;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private IdentifyService identifyService;

    public static IdentifyService getIdentifyService(){
        return instance.identifyService;
    }

    public static PlayerService getPlayerSerivce(){
        return instance.playerService;
    }

    public static RegisterService getRegisterService(){
        return instance.registerService;
    }

    public static ServerConfigValue getServerConfigValue(){
        return instance.serverConfigValue;
    }

    public static AccountService getAccountService(){
        return instance.accountService;
    }

    public static LoginService getLoginService(){
        return instance.loginService;
    }

    public static ConnectService getConnectService(){
        return instance.connectService;
    }

    public static ScenceService getScenceSerivce(){
        return instance.scenceService;
    }

    @Override
    public void setApplicationContext(ApplicationContext contex) throws BeansException {
        this.applicationContext = contex;
    }
}
