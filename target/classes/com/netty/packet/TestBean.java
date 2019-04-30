package com.netty.packet;



public class TestBean {

    private int code;

    private String str;

    @Override
    public String toString() {
        return "TestBean{" +
                "code=" + code +
                ", str='" + str + '\'' +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}
