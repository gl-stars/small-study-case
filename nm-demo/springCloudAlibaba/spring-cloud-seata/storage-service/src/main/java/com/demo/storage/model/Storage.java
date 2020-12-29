package com.demo.storage.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 库存
 *
 * @author: stars
 * @data: 2020年 10月 17日 16:01
 **/
@Data
@Accessors(chain = true)
@TableName("storage_tbl")
public class Storage {
    @TableId
    private Long id;
    private String commodityCode;
    private Long count;
}
