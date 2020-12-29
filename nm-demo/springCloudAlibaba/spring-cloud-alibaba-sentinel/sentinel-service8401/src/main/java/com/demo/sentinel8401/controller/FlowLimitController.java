package com.demo.sentinel8401.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.demo.sentinel8401.handle.CustomerBlockHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class FlowLimitController
{
    @GetMapping("/testA")
    public String testA() {
        System.out.println("testA 调用*******************");
        return "------testA";
    }

    @GetMapping("/testB")
    public String testB() {
        System.out.println("testB 调用*******************");
        return "------testB";
    }

    /***
     * 测试热点数据限流，对应sentinel中的热点规则
     * <p>
     *      @SentinelResource 注解中的value是自己写的，但是要保证唯一。
     *      blockHandler如果访问量超过 Sentinel上的热点规则，那么就调用这里指定的方法处理。
     * </p>
     * @param name
     * @return
     */
    @GetMapping("/ok")
    @SentinelResource(value = "testHotKey",blockHandler = "testHotKey")
    public String find(String name){
        return "OK";
    }

    public String testHotKey(String name, BlockException blockException){
        return "挂了";
    }

    /***
     * 如果违背热点规则，执行 {@link CustomerBlockHandler}类中的
     * {@link CustomerBlockHandler#handlerException(com.alibaba.csp.sentinel.slots.block.BlockException)}方法处理
     * @param name
     * @param userId
     * @return
     */
    @GetMapping("/user")
    @SentinelResource(value = "customerBlockHandler",
            blockHandlerClass = CustomerBlockHandler.class,
            blockHandler = "handlerException")
    public String user(){
        return "这是正常看到的数据";
    }
}
 
 
