package com.yummy.exception;

/**
 * 套餐启用失败异常
 * Set meal enable failed exception
 */
public class SetmealEnableFailedException extends BaseException {

    public SetmealEnableFailedException(){}

    public SetmealEnableFailedException(String msg){
        super(msg);
    }
}
