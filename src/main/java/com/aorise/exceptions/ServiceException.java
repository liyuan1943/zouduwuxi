package com.aorise.exceptions;

/**
 * @author cat
 * @Description  业务逻辑层异常
 * @date  Created in 2018-03-23 13:48
 * @modified By:
 */
public class ServiceException extends RuntimeException {
    public ServiceException(String message) {
        super(message);
    }
}
