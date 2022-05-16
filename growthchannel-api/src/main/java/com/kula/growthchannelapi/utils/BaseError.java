package com.kula.growthchannelapi.utils;

/**
 * business exception code
 */
public class BaseError {

    public static final BaseErrorCode SUCCESS = new BaseErrorCode("000000", "SUCCESS");

    public static final BaseErrorCode FAIL = new BaseErrorCode("000001", "FAIL");

    public static final BaseErrorCode NO_AUTH = new BaseErrorCode("100001", "NOT AUTHENTICATION");

    public static final BaseErrorCode EXPIRE_TOKEN = new BaseErrorCode("100002", "TOKEN HAS EXPIRED");

}
