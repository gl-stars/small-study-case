package com.centre.common.constant;

/**
 * 全局公共常量
 * @author: stars
 * @data: 2020年 08月 11日 21:54
 **/
public interface CommonConstant {

    /**
     * 自定义状态吗
     */
    Integer SUCCESS = 200;
    Integer ERROR = 500;

    /**
     * 默认分页显示的页数
     */
    Integer PAGE_INTEGER = 0;
    Integer LIMIT_INTEGER = 15 ;
    String PAGE_CURRENT = "current";
    String PAGE_SIZE = "size";

    /**
     * 公共日期格式
     */
    String MONTH_FORMAT = "yyyy-MM";
    String DATE_FORMAT = "yyyy-MM-dd";
    String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    String SIMPLE_MONTH_FORMAT = "yyyyMM";
    String SIMPLE_DATE_FORMAT = "yyyyMMdd";
    String SIMPLE_DATETIME_FORMAT = "yyyyMMddHHmmss";
}
