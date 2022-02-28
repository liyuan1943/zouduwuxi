package com.aorise.exceptions;

/**
 * @author cat
 * @Description  数据验证异常
 * @date  Created in 2018-05-24 14:29
 * @modified By:
 */
public class DataValidationException extends RuntimeException{
    public DataValidationException(String messger){
        super(messger);
    }
}
