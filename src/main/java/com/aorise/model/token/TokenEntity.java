package com.aorise.model.token;

import lombok.Data;

import java.io.Serializable;

/**
* @author cat
* @Description token信息实体类
* @date   2018-04-08  10:00
* @modified By:
*/
@Data
public class TokenEntity implements Serializable {

    /**主键id*/
    private Integer id;
    /**token值*/
    private String  token;
    /**凭证有效时间，单位：秒*/
    private Integer expiresIn;
    /**修改时间*/
    private String  editTime;
    /**修改ip*/
    private String  editIp;

}
