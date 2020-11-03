package com.good.word.redis.common.entity;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

public class R implements Serializable {
    private boolean success = true;
    private Integer code = 200;
    private String message = "success";
    private Object data;
    private long ts = System.currentTimeMillis();

    public R() {

    }

    private R setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    private R setCode(Integer code) {
        this.code = code;
        return this;
    }

    private R setMessage(String message) {
        this.message = message;
        return this;
    }

    private R setData(Object data) {
        this.data = data;
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    public long getTs() {
        return ts;
    }

    public static R ok() {
        return new R().setSuccess(true)
                .setCode(HttpStatus.OK.value())
                .setMessage(HttpStatus.OK.getReasonPhrase());
    }

    public static R ok(Object data) {
        return ok().data(data);
    }

    public static R error() {
        return new R().setSuccess(false)
                .setCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }

    public static R error(String message) {
        return error().message(message);
    }

    public static R error(Integer code, String message) {
        return error(message).code(code);
    }

    public R code(Integer code) {
        this.setCode(code);
        return this;
    }

    public R message(String message) {
        this.setMessage(message);
        return this;
    }

    public R data(Object data) {
        this.setData(data);
        return this;
    }

}
