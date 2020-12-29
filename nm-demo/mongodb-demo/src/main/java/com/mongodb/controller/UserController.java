package com.mongodb.controller;

import com.mongodb.model.UserDO;
import com.mongodb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户控制层
 * @author: gl_stars
 * @data: 2020年 09月 09日 16:49
 **/
@RestController
public class UserController {

    @Autowired
    private UserService userService ;

    /**
     * 添加一个用户
     * @param userDO 用户对象
     * @return
     */
    @PostMapping("/insert/one")
    public Boolean insertUserOne(@RequestBody UserDO userDO){
        return userService.insertUserOne(userDO);
    }

    /**
     * 批量添加
     * @param list
     * @return
     */
    @PostMapping("/insert/list")
    public List<UserDO> insertUserList(@RequestBody List<UserDO> list){
        return userService.insertUserList(list);
    }

    /**
     * 更改
     * <p>
     *     更改单个和更改所有都在实现逻辑里面
     * </p>
     * @param nickname
     * @param id
     */
    public void updateUserOne(String nickname,String id){
        userService.updateUserOne(nickname,id);
    }

    /***
     * 各种删除
     * @param id
     */
    public void deleteUser(String id){
        userService.deleteUser(id);
    }
}
