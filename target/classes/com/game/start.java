package com.game;

import com.socket.dispatcher.config.RegistSerializerMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author：xuxin
 * @Date: 2019/4/29 11:35
 */
public class Start {
    private static ClassPathXmlApplicationContext applicationContext;

    private static final Logger logger = LoggerFactory.getLogger(Start.class);

    public static void main(String[] args) {
        logger.info("开始初始化applicationContext...");
        applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        logger.info("开始注册协议....");
        new RegistSerializerMessage().init();
        logger.info("开始初始化通用线程池");
        SpringContext.getCommonExecutorService().init();
        logger.info("开始初始化账号线程池...");
        SpringContext.getAccountExecutorService().init();
        applicationContext.start();
        logger.info("开始构建场景...");
        SpringContext.getScenceSerivce().init();
        logger.info("开启定时器...");
        logger.info("初始化完毕...");
        int port = SpringContext.getServerConfigValue().getPort();
        Server.openBind(port);
    }


}
