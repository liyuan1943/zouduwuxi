package com.aorise.exceptions;

/**
* @author cat
* @Description 微信接口操作异常
* @date   2018-04-16  11:41
* @modified By:
*/
public class WechatException extends RuntimeException {
    /**
     * @author cat
     * @Description 微信接口操作异常
     * @params:
     * @date  2018-04-16  10:25
     * @return 返回值
     * @modified By:
     */
    public WechatException(String  message){
        super(message);
    }
}
