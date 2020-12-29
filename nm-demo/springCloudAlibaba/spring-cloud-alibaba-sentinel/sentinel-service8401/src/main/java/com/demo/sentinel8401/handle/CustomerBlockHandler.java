package com.demo.sentinel8401.handle;

import com.alibaba.csp.sentinel.slots.block.BlockException;

/**
 * 违背热点数据处理类
 * @author: stars
 * @data: 2020年 10月 16日 19:15
 **/
public class CustomerBlockHandler {

    /**
     * 这是降级后处理的方法,这个方法必须为静态的，并且访问修饰符也为public
     * @param exception
     * @return
     */
    public static String handlerException(BlockException exception) {
        return "这里是违背热点规则的处理类。";
    }
}
