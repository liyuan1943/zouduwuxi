package com.aorise.model.wechat;

import lombok.Data;

import java.io.Serializable;

/**
 * @author cat
 * @Description 授权接口凭证
 * @date   2018-04-12  10:00
 * @modified By:
 */
@Data
public class UserAccessTokenModel implements Serializable {
    // 获取到的凭证
    private String token;
    // 凭证有效时间，单位：秒
    private int expiresIn;
    //用户OpenId
    private String openid;
    //用户授权的作用域，使用逗号（,）分隔
    private String scope;

}