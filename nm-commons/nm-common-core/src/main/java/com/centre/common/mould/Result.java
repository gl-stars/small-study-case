package com.centre.common.mould;

import com.centre.common.constant.CommonConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 返回模板
 * @author: stars
 * @data: 2020年 10月 23日 21:48
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    /**
     * 状态码
     */
    private Integer code;

    /***
     * 描述
     */
    private String message;

    /**
     * 返回数据
     */
    private Object data;

    /***
     * 总数
     */
    private Integer total;

    public static Result succeed() {
        return succeedWith(CommonConstant.SUCCESS,"成功",CommonConstant.PAGE_INTEGER,null);
    }
    public static Result succeed(String message){
        return succeedWith(CommonConstant.SUCCESS, message,null,CommonConstant.PAGE_INTEGER);
    }
    public static Result succeed(String message,Integer code){
        return succeedWith(CommonConstant.SUCCESS, message,null,code);
    }
    public static Result succeed(Object data,Integer total){
        return succeedWith(CommonConstant.SUCCESS, "OK",data,total);
    }


    /**
     * 操作成功返回的数据结构
     * @param code 状态吗
     * @param message 描述
     * @param data 数据
     * @param total 总页数
     * @return
     */
    public static Result succeedWith(Integer code, String message, Object data,Integer total) {
        return new Result(code,message,data,total);
    }

    public static Result failed() {
        return failedWith(CommonConstant.ERROR, "操作失败",null,CommonConstant.PAGE_INTEGER);
    }
    public static Result failed(String msg) {
        return failedWith(CommonConstant.ERROR, msg,null,CommonConstant.PAGE_INTEGER);
    }
    public static Result failed(String msg,Integer code) {
        return failedWith(code, msg,null,CommonConstant.PAGE_INTEGER);
    }

    /**
     * 失败返回数据结构
     * 操作成功返回的数据结构
     * @param code 状态吗
     * @param message 描述
     * @param data 数据
     * @param total 总页数
     * @return
     */
    public static Result failedWith(Integer code, String message, Object data,Integer total) {
        return new Result(code,message,data,total);
    }
}
