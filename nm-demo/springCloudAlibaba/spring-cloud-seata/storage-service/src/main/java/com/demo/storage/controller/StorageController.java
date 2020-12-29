package com.demo.storage.controller;

import com.demo.storage.service.StorageService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 库存
 *
 * @author: stars
 * @data: 2020年 10月 17日 16:01
 **/
@RestController
@RequestMapping("storage")
public class StorageController {
    @Resource
    private StorageService storageService;

    /**
     * 减库存
     * @param commodityCode 商品代码
     * @param count 数量
     */
    @RequestMapping(path = "/deduct")
    public Boolean deduct(String commodityCode, Integer count) {
        storageService.deduct(commodityCode, count);
        return true;
    }
}
