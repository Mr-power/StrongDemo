package com.zd.ip2location.bean;

import lombok.Data;

@Data
public class Result<T> {
    private String code;
    private String msg;
    private T data;

    public Result setCode(String code){
        this.code = code;
        return this;
    }
}
