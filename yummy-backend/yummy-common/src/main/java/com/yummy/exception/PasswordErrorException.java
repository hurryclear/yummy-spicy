package com.yummy.exception;

/**
 * 密码错误异常
 * Password error exception
 */
public class PasswordErrorException extends BaseException {

    public PasswordErrorException() {
    }

    public PasswordErrorException(String msg) {
        super(msg);
    }

}
