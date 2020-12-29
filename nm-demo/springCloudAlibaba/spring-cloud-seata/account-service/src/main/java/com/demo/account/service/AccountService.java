package com.demo.account.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.account.mapper.AccountMapper;
import com.demo.account.model.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: stars
 * @data: 2020年 10月 17日 16:01
 **/
@Slf4j
@Service
public class AccountService {
    @Resource
    private AccountMapper accountMapper;

    /**
     * 减账号金额
     */
    //@Transactional(rollbackFor = Exception.class)
    public void reduce(String userId, int money) {
        if ("U002".equals(userId)) {
            throw new RuntimeException("这是一个模拟的异常!!!!");
        }
        QueryWrapper<Account> wrapper = new QueryWrapper<>();
        wrapper.setEntity(new Account().setUserId(userId));
        Account account = accountMapper.selectOne(wrapper);
        account.setMoney(account.getMoney() - money);
        accountMapper.updateById(account);
    }
}
