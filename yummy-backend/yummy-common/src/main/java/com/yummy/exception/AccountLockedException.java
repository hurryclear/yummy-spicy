package com.yummy.exception;

/**
 * 账号被锁定异常
 * Account locked exception
 */
public class AccountLockedException extends BaseException {

    public AccountLockedException() {
    }

    public AccountLockedException(String msg) {
        super(msg);
    }

}
