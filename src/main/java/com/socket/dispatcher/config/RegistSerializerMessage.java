package com.socket.dispatcher.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import javax.annotation.PostConstruct;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/4/30 17:48
 */

public class RegistSerializerMessage {

    private static final String location = "src/main/resources/message.xml";

    public static final Map<Integer, Class<?>> idClassMap = new HashMap<Integer, Class<?>>();

    /**
     * 注册协议
     */

    public void init(){

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = factory.newDocumentBuilder();
            Document parse = db.parse(location);
            NodeList catalogs = parse.getElementsByTagName("catalog");
            for (int i = 0; i < catalogs.getLength(); i++) {
                Node name = catalogs.item(i);
                String sid = name.getAttributes().getNamedItem("id").getNodeValue().toString();
                String svalue = name.getAttributes().getNamedItem("value").getNodeValue().toString();
                int id = Integer.parseInt(sid);
                Class<?> clz = Class.forName(svalue);
                if(idClassMap.containsKey(id)){
                    throw new IllegalArgumentException("协议id：["+id+"] 重复了！");
                }
                idClassMap.put(id, clz);
            }
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }
}
