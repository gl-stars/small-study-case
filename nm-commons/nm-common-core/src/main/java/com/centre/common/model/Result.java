package com.centre.common.model;

import com.centre.common.constant.CommonConstant;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据返回格式
 * <p>
 *  {
 * 	    "code": 0, 状态吗
 * 	    "message": "没毛病", 描述
 * 	    "data": [{ 数据域
 * 		    "count": 120, 总共页数
 * 		    "list": [] 数据列表
 *       }]
 * }
 * </p>
 * @author: stars
 * @data: 2020年 08月 11日 19:08
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> implements Serializable {

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
    private DataField<T> data;

    public static <T> Result<T> succeed() {
        return succeedWith(CommonConstant.SUCCESS,"成功",CommonConstant.PAGE_INTEGER,null);
    }
    public static <T> Result<T> succeed(String msg) {
        return succeedWith(CommonConstant.SUCCESS,msg,CommonConstant.PAGE_INTEGER,null);
    }

    public static <T> Result<T> succeed(List<T> list, Integer count) {
        return succeedWith(CommonConstant.SUCCESS,"操作成功",count,list);
    }

    public static <T> Result<T> succeed(T t) {
        List<T> list = new ArrayList<>();
        list.add(t);
        return succeedWith(CommonConstant.SUCCESS,"操作成功",CommonConstant.PAGE_INTEGER,list);
    }
    public static <T> Result<T> succeed(List<T> list) {
        return succeedWith(CommonConstant.SUCCESS,"操作成功",CommonConstant.PAGE_INTEGER,list);
    }

    public static <T> Result<T> succeed(List<T> list, Long count) {
        return succeedWith(CommonConstant.SUCCESS,"操作成功",Math.toIntExact(count),list);
    }

    public static <T> Result<T> succeed(List<T> list, String msg) {
        return succeedWith(CommonConstant.SUCCESS,msg,CommonConstant.PAGE_INTEGER,list);
    }

    /**
     * 操作成功返回的数据结构
     * @param code 状态吗
     * @param msg 描述
     * @param count 总页数
     * @param list 数据
     * @param <T> 泛型
     * @return
     */
    public static <T> Result<T> succeedWith(Integer code, String msg, Integer count, List<T> list) {
        return new Result<T>(code, msg, new DataField<T>(count,list));
    }

    public static <T> Result<T> failed() {
        return failedWith(CommonConstant.ERROR, "操作失败", CommonConstant.PAGE_INTEGER,null);
    }

    public static <T> Result<T> failed(String msg) {
        return failedWith(CommonConstant.ERROR, msg,CommonConstant.PAGE_INTEGER,null);
    }
    public static <T> Result<T> failed(Integer code,String msg) {
        return failedWith(code, msg,1,null);
    }

    public static <T> Result<T> failed(List<T> list, String msg) {
        return failedWith(CommonConstant.ERROR,msg,CommonConstant.PAGE_INTEGER,list);
    }

    /**
     * 失败返回数据结构
     * @param code 状态吗
     * @param msg 描述
     * @param count 总页数
     * @param list 数据
     * @param <T> 泛型类型
     * @return
     */
    public static <T> Result<T> failedWith(Integer code, String msg, Integer count, List<T> list) {
        return new Result<T>(code, msg, new DataField<T>(count,list));
    }

}
