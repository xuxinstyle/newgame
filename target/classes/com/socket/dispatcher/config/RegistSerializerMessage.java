package com.socket.dispatcher.config;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/4/30 17:48
 */

public class RegistSerializerMessage {

    private static final String LOCATION = "src/main/resources/message.xml";

    public static final Map<Integer, Class<?>> ID_CLASS_MAP = new HashMap<Integer, Class<?>>();

    /**
     * 注册协议
     */

    public void init(){

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = factory.newDocumentBuilder();
            Document parse = db.parse(LOCATION);
            NodeList catalogs = parse.getElementsByTagName("catalog");
            for (int i = 0; i < catalogs.getLength(); i++) {
                Node name = catalogs.item(i);
                String sid = name.getAttributes().getNamedItem("id").getNodeValue().toString();
                String svalue = name.getAttributes().getNamedItem("value").getNodeValue().toString();
                int id = Integer.parseInt(sid);
                Class<?> clz = Class.forName(svalue);
                if(ID_CLASS_MAP.containsKey(id)){
                    throw new IllegalArgumentException("协议id：["+id+"] 重复了！");
                }
                ID_CLASS_MAP.put(id, clz);
            }
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }
}
