package com.aorise.model.wechat;

import lombok.Data;

/**
 * @author cat
 * @Description 通用接口凭证
 * @date   2018-04-12  10:00
 * @modified By:
 */
@Data
public class AccessTokenModel {
    // 获取到的凭证
    private String token;
    // 凭证有效时间，单位：秒
    private int expiresIn;

}