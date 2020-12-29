package com.demo.account.controller;

import com.demo.account.service.AccountService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: stars
 * @data: 2020年 10月 17日 16:01
 **/
@RestController
public class AccountController {
    @Resource
    private AccountService accountService;

    /**
     * 账号扣钱
     */
    @PostMapping(value = "/account/reduce")
    public Boolean reduce(String userId, Integer money) {
        accountService.reduce(userId, money);
        return true;
    }
}
