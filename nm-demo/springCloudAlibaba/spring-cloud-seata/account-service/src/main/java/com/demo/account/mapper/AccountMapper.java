package com.demo.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.account.model.Account;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: stars
 * @data: 2020年 10月 17日 16:01
 **/
@Mapper
public interface AccountMapper extends BaseMapper<Account> {
}
