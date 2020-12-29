package com.mongodb.service;

import com.mongodb.model.PeopleDO;

import java.util.List;

/**
 * @author: gl_stars
 * @data: 2020年 09月 12日 11:53
 **/
public interface PeopleService {

    /**
     * 添加测试数据
     *
     * @return 返回添加的数据并且将添加进去的编号也赋值在里面了。
     */
    List<PeopleDO> addTestData(List<PeopleDO> list);

    /***
     * 查询全部
     * @param page 当前页
     * @param size 每页显示的数据条数
     * @return
     */
    List<PeopleDO> findall(Integer page, Integer size);

    /**
     * 返回指定字段
     * @return
     */
    List<PeopleDO> findCheckField();


    /***
     * 普通条件查询
     * @param page 当前页
     * @param size 每页显示的条数
     * @return
     */
    List<PeopleDO> findCondition(Integer page,Integer size);


    /**
     * 删除
     * @return
     */
    Boolean delete();


    /**
     * 更改
     * @return
     */
    Boolean upadtePeople();


    Boolean updateUpsert();
}