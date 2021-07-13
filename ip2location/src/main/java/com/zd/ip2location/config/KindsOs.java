package com.zd.ip2location.config;

public enum KindsOs {
    UNIX(255,"UNIX系统"),
    LINUX(64,"LINUX系统orWindows系统");

    private Integer ttl;
    private String msg;
    KindsOs(Integer ttl,String msg){
        this.msg=msg;
        this.ttl=ttl;
    }

    public Integer getTtl() {
        return ttl;
    }

    public String getMsg() {
        return msg;
    }
}
