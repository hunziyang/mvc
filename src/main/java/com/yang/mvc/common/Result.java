package com.yang.mvc.common;

import java.io.Serializable;

public class Result<T> implements Serializable {

    /**
     * 响应状态码
     */
    private Integer code;

    /**
     * 响应信息
     */
    private String message;

    /**
     * 响应对象
     */
    private T data;

    private Result() {
    }

    private Result(T data) {
        this.data = data;
    }

    private Result(String message) {
        this.message = message;
    }

    private Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static Result success() {
        return new Result(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), null);
    }

    public static <E> Result<E> success(E object) {
        return new Result(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), object);
    }

    public static <E> Result<E> success(Integer code, String message, E object) {
        return new Result(code, message, object);
    }

    public static Result error() {
        return new Result(ResultCode.FAILED.getCode(), ResultCode.FAILED.getMessage(), null);
    }

    public static Result error(ResultCode code) {
        return new Result(code.getCode(), code.getMessage(), null);
    }

    public static Result error(String message) {
        return new Result(ResultCode.FAILED.getCode(), message, null);
    }

    public static Result error(Integer code, String message) {
        return new Result(code, message, null);
    }

    public static <E> Result<E> error(Integer code, String message, E object) {
        return new Result(code, message, object);
    }
}
