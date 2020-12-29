package com.centre.common.enumeration;

/**
 * 方法类型
 * @author: stars
 * @data: 2020年 10月 05日 22:26
 **/
public enum MethodTypeEnum {
    POST("POST"),
    GET("GET"),
    PUT("PUT"),
    PATCH("PATCH"),
    DELETE("DELETE");

    /**
     * 方法名称
     */
    private String msg;

    MethodTypeEnum(String msg){
        this.msg = msg;
    }
    public String getCode() {
        return this.msg;
    }
}
