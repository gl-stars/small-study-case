package com.oauth2.security.controller;

import com.oauth2.security.user.model.SysPermission;
import com.oauth2.security.user.service.SysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 各种前端请求测试
 * @author: stars
 * @create: 2020年 07月 08日 10:47
 **/
@RestController
public class MiscellaneousController {

    @Autowired
    private SysPermissionService sysPermissionService ;

    @GetMapping("/list")
    public List findData(){
        List<String> list = new ArrayList<>();
        list.add("双肩包");
        list.add("小皮包包");
        list.add("小宝宝");
        list.add("大宝宝");
        return list;
    }

    @GetMapping("/find")
    public Object findPermissionService(){
        // 查询所有数据
        List<SysPermission> addALL = sysPermissionService.list();
        for (SysPermission sms : addALL){
            // 处理数据节点关系
            this.nodeHandle(sms,addALL);
        }
        return addALL;
    }

    /***
     * 查询子节点数据
     * @param region
     */
    private void nodeHandle(SysPermission sms,List<SysPermission> addALL) {
        // 过滤出id = parentId 的所有数据并保存到集合中
        List<SysPermission> collect = addALL.stream().filter(s -> sms.getId().intValue() == s.getParentId().intValue()).collect(Collectors.toList());
        // 处理节点下的节点数据，有些数据可能一个节点下还存在，所以需要使用到递归。
        for (int i=0 ;i<collect.size() ;i++){
            SysPermission smsadd = collect.get(i);
            this.nodeHandle(smsadd,addALL);
        }
        sms.setChildren(collect);
    }
}
