package com.kula.growthchannelapi.config;

import com.kula.growthchannelapi.utils.BaseBizException;
import com.kula.growthchannelapi.utils.BaseResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BaseExpHandler {

    @ExceptionHandler(BaseBizException.class)
    public BaseResponse<Void> baseBizExceptionHandler(BaseBizException ex) {
        return BaseResponse.fail(ex.getCode(), ex.getMsg());
    }


}
