package com.demo.nacos1.controller;

import com.demo.nacos1.config.NacosConfig;
import com.demo.nacos1.properties.NacosDemoProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 查看实时配置是否有效
 * <p>@RefreshScope 表示实时刷新</p>
 * @author: stars
 * @data: 2020年 10月 11日 09:34
 **/
@RestController
@RequestMapping("/config")
public class ConfigController {

    /**
     * 自定义配置类
     */
    @Autowired
    private NacosDemoProperties nacosProperties ;

    /***
     * 服务间调用
     */
    @Autowired
    private RestTemplate restTemplate ;

    /**
     * 获取到yml配置
     * @return
     */
    @GetMapping("/find")
    public Object findConfig(){
        return nacosProperties.getName() + " 、 " + nacosProperties.getPrice() ;
    }

    /**
     * 调用服务器名为 nacosTwo ，并且地址为：brook2第方法
     * <b>
     *     注意：{@link NacosConfig}这里没有在 RestTemplate 实例上打上 @LoadBalanced 注解，那么
     *     调用的时候不能指定微服务名称，而是微服务的地址，例如：http://localhost:4477/brook2
     * </b>
     * @return
     */
    @GetMapping("/brook1")
    public Object findConfigaaa(){
        System.out.println("服务器为nacosOne******************");
        return restTemplate.getForEntity("http://nacosTwo/brook2/" + "这里添加一个名字",String.class);
    }
}
