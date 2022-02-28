package com.aorise.exceptions;

/**
 * @author cat
 * @Description   用户异常
 * @date  Created in 2018/10/10 15:49
 * @modified By:
 */
public class UserCorrelationException extends RuntimeException{

    public UserCorrelationException(String message) {
        super(message);
    }
}
