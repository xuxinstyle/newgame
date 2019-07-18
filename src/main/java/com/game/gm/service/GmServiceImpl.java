package com.game.gm.service;

import com.game.SpringContext;
import com.game.common.exception.RequestException;
import com.game.gm.model.GMCommand;
import com.game.gm.packet.SM_GMCommond;
import com.game.user.account.entity.AccountEnt;
import com.game.user.account.model.AccountInfo;
import com.game.util.SendPacketUtil;
import com.socket.core.session.TSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/6/28 18:03
 */
@Component
public class GmServiceImpl implements GmService {
    private static final Logger logger = LoggerFactory.getLogger(GmServiceImpl.class);
    @Autowired
    private ConversionService conversionService;

    private Map<String, Method> commandMethods = new HashMap<>();

    @PostConstruct
    private void init(){
        Method[] methods = GMCommand.class.getMethods();
        for(Method method:methods){
            commandMethods.put(method.getName(),method);
        }
    }

    @Override
    public void doGmCommand(TSession session, String command) {
        if(!checkGm(session)){
            SM_GMCommond sm = new SM_GMCommond();
            sm.setStatus(2);
            session.sendPacket(sm);
            return;
        }
        realDoGmCommand(session,command);
    }

    private void realDoGmCommand(TSession session, String command) {
        try {
            String[] split = command.split(" ");
            Method method = commandMethods.get(split[0]);

            Class<?>[] parameterTypes = method.getParameterTypes();
            Object[] params = new Object[parameterTypes.length];
            params[0] = session.getAccountId();
            for (int i = 1; i<parameterTypes.length; i++){
                params[i] = conversionService.convert(split[i],parameterTypes[i]);
            }
            method.invoke(new GMCommand(),params);
            SM_GMCommond sm = new SM_GMCommond();
            sm.setStatus(1);
            session.sendPacket(sm);
        } catch (Exception e) {
            logger.warn("GM命令[{}]执行错误", command);
            SM_GMCommond sm = new SM_GMCommond();
            sm.setStatus(2);
            session.sendPacket(sm);
        }
    }

    private boolean checkGm(TSession session) {

        String accountId = session.getAccountId();
        AccountEnt accountEnt = SpringContext.getAccountService().getAccountEnt(accountId);
        AccountInfo accountInfo = accountEnt.getAccountInfo();
        if(accountInfo.isGm()){
            return true;
        }
        return false;
    }
}
