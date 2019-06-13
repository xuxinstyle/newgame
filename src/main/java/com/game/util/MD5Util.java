package com.game.util;


import org.apache.commons.codec.digest.DigestUtils;

/**
 * @Author：xuxin
 * @Date: 2019/6/12 10:43
 */
public class MD5Util {

    public static final String saltDB ="3a4c5v";
    private static final String salt = "1a2b3c4d";

    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }
    //这个是在网络中传输的，哪怕被截获到了。反向获取的话还是找不到真正的密码的
    public static String inputPassToFormPass(String inputPass) { //拼个串在做md5.当然这个拼接自定义的。
        String str = "" + salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    //把传上来的密码加上随机 salt再次md5存入mysql
    public static String formPassToDBPass(String formPass, String salt) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + formPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }
    //把明文二次MD5为存入数据库
    public static String inputPassToDbPass(String input, String saltDB) {
        String formPass = inputPassToFormPass(input);
        return formPassToDBPass(formPass, saltDB);
    }



}
