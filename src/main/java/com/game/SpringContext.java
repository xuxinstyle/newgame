package com.game;

import com.event.core.EvenManager;
import com.game.base.identify.service.SessionService;
import com.game.base.executor.account.AccountExecutorService;
import com.game.base.executor.common.CommonExecutorService;
import com.game.base.executor.scene.SceneExecutorService;
import com.game.gm.service.GmService;
import com.game.gm.service.GmServiceImpl;
import com.game.user.account.service.AccountService;
import com.game.base.identify.service.IdentifyService;
import com.game.role.player.service.PlayerService;
import com.game.connect.service.ConnectService;
import com.game.login.service.LoginService;
import com.game.scence.service.ScenceService;
import com.game.register.service.RegisterService;
import com.game.role.equip.service.EquipService;
import com.game.role.equipupgrade.service.EquipUpgradeService;
import com.game.user.item.service.ItemService;
import com.game.user.itemeffect.service.ItemEffectService;
import com.game.role.equipstren.service.EquipStrenService;
import com.resource.core.StorageManager;
import com.socket.core.session.SessionManager;
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
    private void init(){
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
    @Autowired
    private SessionManager sessionManager;
    @Autowired
    private AccountExecutorService accountExecutorService;
    @Autowired
    private CommonExecutorService commonExecutorService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private EquipService equipService;
    @Autowired
    private EvenManager evenManager;
    @Autowired
    private EquipStrenService equipStrenService;
    @Autowired
    private EquipUpgradeService equipUpgradeService;
    @Autowired
    private ItemEffectService itemEffectService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private SceneExecutorService sceneExecutorService;
    @Autowired
    private StorageManager storageManager;
    @Autowired
    private GmService gmService;

    public static GmService getGmService(){
        return instance.gmService;
    }

    public static StorageManager getStorageManager(){
        return instance.storageManager;
    }

    public static SceneExecutorService getSceneExecutorService(){
        return instance.sceneExecutorService;
    }

    public static SessionService getSessionService(){
        return instance.sessionService;
    }

    public static ItemEffectService getItemEffectService(){
        return instance.itemEffectService;
    }

    public static EquipUpgradeService getEquipUpgradeService(){
        return instance.equipUpgradeService;
    }

    public static EquipStrenService getStrenEquipService(){
        return instance.equipStrenService;
    }

    public static EvenManager getEvenManager(){
        return instance.evenManager;
    }

    public static EquipService getEquipService(){
        return instance.equipService;
    }

    public static ItemService getItemService(){
        return instance.itemService;
    }

    public static CommonExecutorService getCommonExecutorService() {
        return instance.commonExecutorService;
    }

    public static AccountExecutorService getAccountExecutorService(){
        return instance.accountExecutorService;
    }

    public static SessionManager getSessionManager(){
        return instance.sessionManager;
    }

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
