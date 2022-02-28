package com.aorise.utils.json;

import com.aorise.utils.StatusDefine;
import com.aorise.utils.StatusDefineMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by jzwx on 2019/01/30.
 * Rest接口返回数据封装bean
 */
public class JsonResponseData {
    private static Gson gson = new GsonBuilder().create();

    //空构造方法
    public JsonResponseData() {
    }

    //构造方法
    public JsonResponseData(Boolean ret, String msg, int status, String message, Object data) {
        this.ret = ret;
        this.msg = msg;
        this.code = status;
        this.message = message;
        this.data = data == null ? new Object() : data;
    }

    //是否成功
    private Boolean ret     = false;

    //返回信息
    private String  msg     = null;

    //返回状态
    private int     code    = -100;

    //返回状态对应消息
    private String  message = null;

    //返回数据
    private Object  data    = null;

    /**
     * 错误信息处理方法
     * @param code
     * @param message
     * @Auth jzwx
     * @return String
     */
    public static String ofMessage(int code, String message) {
        return new JsonResponseData(Boolean.FALSE, "", code, message, null).toString();
    }

    /**
     * 数据校验错误信息处理方法
     * @param message
     * @Auth jzwx
     * @return String
     */
    public static String ofDataValidationMessage(String message) {
        return new JsonResponseData(Boolean.FALSE, StatusDefineMessage.getMessage(StatusDefine.DATA_FORMAT_ERROR), StatusDefine.DATA_FORMAT_ERROR, message, null).toString();
    }

    /**
     * 操作成功处理
     * @param data
     * @Auth jzwx
     * @return
     */
    public static String ofSuccess(Object data){
        return new JsonResponseData(Boolean.TRUE, "",
                StatusDefine.SUCCESS, StatusDefineMessage.getMessage(StatusDefine.SUCCESS), data).toString();
    }

    /**
     * 操作失败处理方法
     * @Auth jzwx
     * @return String
     */
    public static String ofFailure(){
        return new JsonResponseData(Boolean.TRUE, "",
                StatusDefine.FAILURE, StatusDefineMessage.getMessage(StatusDefine.FAILURE), null).toString();
    }

    /**
     * Getter method for property <tt>ret</tt>.
     *
     * @return property value of ret
     */
    public Boolean getRet() {
        return ret;
    }

    /**
     * Setter method for property <tt>counterType</tt>.
     *
     * @param ret value to be assigned to property ret
     */
    public void setRet(Boolean ret) {
        this.ret = ret;
    }

    /**
     * Getter method for property <tt>msg</tt>.
     *
     * @return property value of msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * Setter method for property <tt>counterType</tt>.
     *
     * @param msg value to be assigned to property msg
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * Getter method for property <tt>code</tt>.
     *
     * @return property value of code
     */
    public int getCode() {
        return code;
    }

    /**
     * Setter method for property <tt>counterType</tt>.
     *
     * @param code value to be assigned to property code
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * Getter method for property <tt>message</tt>.
     *
     * @return property value of message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Setter method for property <tt>counterType</tt>.
     *
     * @param message value to be assigned to property message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Getter method for property <tt>data</tt>.
     *
     * @return property value of data
     */
    public Object getData() {
        return data;
    }

    /**
     * Setter method for property <tt>counterType</tt>.
     *
     * @param data value to be assigned to property data
     */
    public void setData(Object data) {
        this.data = data;
    }

    //    重写toString方法
    @Override
    public String toString() {
        return gson.toJson(this);
    }
}
