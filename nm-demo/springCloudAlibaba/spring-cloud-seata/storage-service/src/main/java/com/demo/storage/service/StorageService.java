package com.demo.storage.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.storage.mapper.StorageMapper;
import com.demo.storage.model.Storage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: stars
 * @data: 2020年 10月 17日 16:01
 **/
@Slf4j
@Service
public class StorageService {
    @Resource
    private StorageMapper storageMapper;

    /**
     * 减库存
     * 
     * @param commodityCode 商品编号
     * @param count 数量
     */
    //@Transactional(rollbackFor = Exception.class)
    public void deduct(String commodityCode, int count) {
        QueryWrapper<Storage> wrapper = new QueryWrapper<>();
        wrapper.setEntity(new Storage().setCommodityCode(commodityCode));
        Storage storage = storageMapper.selectOne(wrapper);
        storage.setCount(storage.getCount() - count);

        storageMapper.updateById(storage);
    }
}
