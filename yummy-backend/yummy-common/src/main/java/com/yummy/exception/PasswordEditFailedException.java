package com.yummy.exception;

/**
 * 密码修改失败异常
 * Password modification failed exception
 */
public class PasswordEditFailedException extends BaseException{

    public PasswordEditFailedException(String msg){
        super(msg);
    }

}
