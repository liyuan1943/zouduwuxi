package com.aorise.exceptions;

/**
 * @author cat
 * @Description
 * @date  Created in 2018/10/16 11:26
 * @modified By:
 */
public class LoginOvertimeException extends RuntimeException {

    public LoginOvertimeException(String  message){
        super(message);
    }
}
