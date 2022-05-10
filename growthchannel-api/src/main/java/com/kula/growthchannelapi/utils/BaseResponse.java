package com.kula.growthchannelapi.utils;

import lombok.Getter;


public class BaseResponse<T> {

    @Getter
    private String code;

    @Getter
    private String msg;

    @Getter
    private T data;

    public BaseResponse(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public BaseResponse() {
    }

    public static <T> BaseResponse<T> success() {
        return response(BaseError.SUCCESS, null);
    }

    public static <T> BaseResponse<T> success(T data) {
        return response(BaseError.SUCCESS, data);
    }


    public static BaseResponse<Void> fail() {
        return response(BaseError.FAIL, null);
    }


    public static BaseResponse<Void> fail(String code, String msg) {
        return new BaseResponse<>(code, msg, null);
    }


    public static BaseResponse<Void> fail(String msg) {
        return BaseResponse.fail(BaseError.FAIL.getCode(), msg);
    }


    public static <T> BaseResponse<T> response(BaseErrorCode baseErrorCode, T data) {
        return new BaseResponse<>(baseErrorCode.getCode(), baseErrorCode.getMsg(), data);
    }


}
