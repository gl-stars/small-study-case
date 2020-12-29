package com.demo.order.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 订单
 *
 * @author: stars
 * @data: 2020年 10月 17日 16:01
 **/
@Data
@Accessors(chain = true)
@TableName("order_tbl")
public class Order {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String userId;
    private String commodityCode;
    private Integer count;
    private Integer money;
}
