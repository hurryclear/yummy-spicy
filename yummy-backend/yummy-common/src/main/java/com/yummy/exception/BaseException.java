package com.yummy.exception;

/**
 * 业务异常
 * Business exception
 */
public class BaseException extends RuntimeException {

    public BaseException() {
    }

    public BaseException(String msg) {
        super(msg);
    }

}
