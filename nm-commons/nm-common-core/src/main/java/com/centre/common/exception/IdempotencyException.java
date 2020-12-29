package com.centre.common.exception;

/**
 * 幂等性异常
 * <p>业务异常说明需要事务回滚，所以必须继承 {@link RuntimeException}</p>
 * @author: stars
 * @data: 2020年 10月 03日 16:19
 **/
public class IdempotencyException extends RuntimeException {
    private static final long serialVersionUID = 6610083281801529147L;

    public IdempotencyException(String message) {
        super(message);
    }
}
