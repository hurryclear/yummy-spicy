package com.yummy.result;

import lombok.Data;

import java.io.Serializable;

/**
 * 后端统一返回结果
 * Backend unified return result
 * @param <T>
 */
@Data
public class Result<T> implements Serializable {

    private Integer code; //编码：1成功，0和其它数字为失败
    // Code: 1 for success, 0 and other numbers for failure
    private String msg; //错误信息
    // Error message
    private T data; //数据
    // Data

    public static <T> Result<T> success() {
        Result<T> result = new Result<T>();
        result.code = 1;
        return result;
    }

    public static <T> Result<T> success(T object) {
        Result<T> result = new Result<T>();
        result.data = object;
        result.code = 1;
        return result;
    }

    public static <T> Result<T> error(String msg) {
        Result result = new Result();
        result.msg = msg;
        result.code = 0;
        return result;
    }

}
