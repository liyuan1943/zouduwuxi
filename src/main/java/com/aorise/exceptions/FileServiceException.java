package com.aorise.exceptions;


/**
 * @author cat
 * @Description   文件服务器异常
 * @date  Created in 2017-09-05 10:28
 * @modified By:
 */
public class FileServiceException extends RuntimeException {
    public FileServiceException(String  message){
        super(message);
    }
}
