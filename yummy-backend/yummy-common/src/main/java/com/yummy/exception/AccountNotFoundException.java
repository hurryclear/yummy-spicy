package com.yummy.exception;

/**
 * 账号不存在异常
 * Account not found exception
 */
public class AccountNotFoundException extends BaseException {

    public AccountNotFoundException() {
    }

    public AccountNotFoundException(String msg) {
        super(msg);
    }

}
