package com.yummy.exception;

/**
 * 登录失败
 * Login failed
 */
public class LoginFailedException extends BaseException{
    public LoginFailedException(String msg){
        super(msg);
    }
}
