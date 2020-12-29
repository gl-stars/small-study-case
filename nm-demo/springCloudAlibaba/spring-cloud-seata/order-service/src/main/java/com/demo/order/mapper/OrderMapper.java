package com.demo.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.order.model.Order;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: stars
 * @data: 2020年 10月 17日 16:01
 **/
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

}
