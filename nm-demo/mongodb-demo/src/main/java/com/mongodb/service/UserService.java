package com.mongodb.service;

import com.mongodb.model.UserDO;

import java.util.List;

/**
 * @author: gl_stars
 * @data: 2020年 09月 09日 16:52
 **/
public interface UserService {

    /**
     * 添加一条数据
     * @return
     */
    Boolean insertUserOne(UserDO userDO);

    /**
     * 批量添加
     * @param list
     * @return
     */
    List<UserDO> insertUserList(List<UserDO> list) ;

    void updateUserOne(String nickname,String id);

    void updateUserOne(UserDO userDO);


    /**
     * 删除
     * @param id
     */
    void deleteUser(String id);
}
