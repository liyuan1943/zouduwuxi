package com.aorise.model.systemlog;

/**
 * @Description 系统日志操作实体
 * @author cat
 * @date  2018/10/10 14:49
 * @Version V1.0
 */
public class SystemLogModel {
    private Integer id;
    private String ip;//客户端IP
    private String requestapi;//请求的API
    private String httptype;//Http请求方式
    private String actionmethod;//请求类方法
    private String params;//请求参数
    private String actiondate;//请求发起时间
    private String returndata;//请求返回数据
    private String type;//请求返回状态 1 成功 2失败
    private String returndate;//请求返回时间
    private String userid;// 操作人ID
    private String modules;//操作模块
    private String description;//操作内容
    private String exceptioncode;// 异常code
    private String exceptiondetail;// 异常详情

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getRequestapi() {
        return requestapi;
    }

    public void setRequestapi(String requestapi) {
        this.requestapi = requestapi;
    }

    public String getHttptype() {
        return httptype;
    }

    public void setHttptype(String httptype) {
        this.httptype = httptype;
    }

    public String getActionmethod() {
        return actionmethod;
    }

    public void setActionmethod(String actionmethod) {
        this.actionmethod = actionmethod;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getActiondate() {
        return actiondate;
    }

    public void setActiondate(String actiondate) {
        this.actiondate = actiondate;
    }

    public String getReturndata() {
        return returndata;
    }

    public void setReturndata(String returndata) {
        this.returndata = returndata;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReturndate() {
        return returndate;
    }

    public void setReturndate(String returndate) {
        this.returndate = returndate;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getModules() {
        return modules;
    }

    public void setModules(String modules) {
        this.modules = modules;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExceptioncode() {
        return exceptioncode;
    }

    public void setExceptioncode(String exceptioncode) {
        this.exceptioncode = exceptioncode;
    }

    public String getExceptiondetail() {
        return exceptiondetail;
    }

    public void setExceptiondetail(String exceptiondetail) {
        this.exceptiondetail = exceptiondetail;
    }
}
