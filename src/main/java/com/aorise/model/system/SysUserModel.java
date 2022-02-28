package com.aorise.model.system;

import java.io.Serializable;
import java.util.List;

/**
 * @author cat
 * @Description
 * @date  Created in 2018/9/12 10:50
 * @modified By:
 */
public class SysUserModel implements Serializable{

    private Integer id;
    private String userName;
    private String passWord;
    private String name;
    private String phoneNum;
    private String idNumber;
    private Integer role;
    private Integer level;
    private Integer isInitialPwd;

    public Integer getIsInitialPwd() {
        return isInitialPwd;
    }

    public void setIsInitialPwd(Integer isInitialPwd) {
        this.isInitialPwd = isInitialPwd;
    }

    private String token;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
//    private String deptId;
//    private String fullName;
//    private String adder;
//    private String adderName;
//    private String phone;
//    private String groups;
//
//    private String groupsName;//用户组名称
//
//    private Integer state;
//    private Integer creater;
//    private String createTime;
//    private Integer editer;
//    private String editeTime;
//
//    private Integer unreadInform;//通知未读
//    private Integer isInitialPwd;//是否初始密码


}
