package com.good.word.redis.exception;

public class BizException extends RuntimeException {
    private Integer errCode;

    public Integer getErrCode() {
        return errCode;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }

    public BizException() {
        super();
    }

    public BizException(Integer errCode,String message) {
        super(message);
        this.errCode = errCode;
    }

    public BizException(Integer errCode, String message, Throwable cause) {
        super(message, cause);
        this.errCode = errCode;
    }

}
