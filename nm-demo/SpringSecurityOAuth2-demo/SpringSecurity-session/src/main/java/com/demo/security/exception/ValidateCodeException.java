package com.demo.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 验证码异常处理类
 * <p>使用验证码登录时，需要判断验证码是否正确，如果验证码正确则通过，不正确则抛出异常，抛出来的异常就在这里捕获。</p>
 * @author: stars
 * @data: 2020年 10月 01日 16:44
 **/
public class ValidateCodeException extends AuthenticationException {

    public ValidateCodeException(String msg, Throwable t) {
        super(msg, t);
    }

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
