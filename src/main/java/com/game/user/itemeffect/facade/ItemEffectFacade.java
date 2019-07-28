package com.game.user.itemeffect.facade;

import com.event.anno.EventAnn;
import com.game.SpringContext;
import com.game.login.event.LoginEvent;
import com.game.role.player.event.LogoutEvent;
import org.springframework.stereotype.Component;

/**
 * @Author：xuxin
 * @Date: 2019/6/21 15:04
 */
@Component
public class ItemEffectFacade {
    @EventAnn
    public void doLoginAfter(LoginEvent event){
        SpringContext.getItemEffectService().doLoginAfter(event.getPlayer().getAccountId());
    }
    @EventAnn
    public void doLogoutAfter(LogoutEvent event){
        SpringContext.getItemEffectService().doLogoutAfter(event);
    }
}
