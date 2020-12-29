package com.mongodb.controller;

import com.mongodb.model.PeopleDO;
import com.mongodb.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * 各种查询
 * <p>mongodb的各种查询，包括简单查询，复杂查询，聚合查询，分组查询，各种条件筛选等等。</p>
 * @author: gl_stars
 * @data: 2020年 09月 12日 10:25
 **/
@RestController
@RequestMapping("/people")
public class FindController {

    @Autowired
    private PeopleService peopleService ;

    /***
     * 批量添加
     * @return
     */
    @GetMapping("/add")
    public List<PeopleDO> addTestData(){
        // 创造 1000 条数据保存到集合中
        List<PeopleDO> list = new ArrayList<>(1001) ;
        for (int i = 0; i < 10000; i++) {
            PeopleDO peopleDO = new PeopleDO() ;
            peopleDO.setUserId((100L + i));

            // 如果年龄大于i 大于 18 并小于 35 就将i最为年龄(循环次数过多会与偏差)
            peopleDO.setAge(new Random().nextInt(35) +18);

            // i为奇数 状态为 1
            if ((i+1) % 2 == 0){
                peopleDO.setStatus(0);
            }else {
                peopleDO.setStatus(1);
            }
            list.add(peopleDO);
        }

        // 批量添加数据
        return peopleService.addTestData(list);
    }


    /***
     * 查询全部
     * @param page 当前页
     * @param size 每页显示数据的条数
     * @return
     */
    @GetMapping("/find")
    public List<PeopleDO> findAll(@RequestParam(value = "page",defaultValue = "1") Integer page,@RequestParam(value = "size",defaultValue = "20") Integer size){
        return peopleService.findall(page,size);
    }

    /***
     * 返回指定字段
     * @return
     */
    @GetMapping("/check/field")
    public List<PeopleDO> findCheckField(){
        return peopleService.findCheckField() ;
    }

    /**
     * 普通条件查询
     * @param page 当前页
     * @param size 每页显示的数据条数
     * @return
     */
    @GetMapping("/condition")
    public List<PeopleDO> findConditions(@RequestParam(value = "page",defaultValue = "0") Integer page,
                                         @RequestParam(value = "size",defaultValue = "20") Integer size){
        return peopleService.findCondition(page,size);
    }

    /**
     * 各种删除
     * @return
     */
    @DeleteMapping("/delete")
    public Boolean deletePeople(){
        return peopleService.delete();
    }

    /**
     * 各种更改
     * @return
     */
    @PutMapping("/update")
    public Boolean updatePeopel(){
        return peopleService.upadtePeople() ;
    }

    @PatchMapping("/update")
    public Boolean findBulkOps(){
        return peopleService.updateUpsert();
    }
}

