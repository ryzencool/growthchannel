package com.kula.growthchannelapi.utils;

import lombok.Getter;

public class BaseBizException extends RuntimeException {

    @Getter
    private String code;

    @Getter
    private String msg;

    public BaseBizException() {
        super();
    }

    public BaseBizException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public BaseBizException(String code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }


    public BaseBizException(BaseErrorCode err) {
        super();
        this.code = err.getCode();
        this.msg = err.getMsg();
    }


}