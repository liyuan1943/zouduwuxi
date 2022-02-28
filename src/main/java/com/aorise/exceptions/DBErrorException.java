package com.aorise.exceptions;

/**
 * @Description
 * @author cat
 * @date  2018/9/20 16:53
 * @Version V1.0
 */
public class DBErrorException extends RuntimeException {
    public DBErrorException(String message) {
        super(message);
    }
}
