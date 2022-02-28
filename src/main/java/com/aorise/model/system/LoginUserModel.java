package com.aorise.model.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
* 登录账号
* @author cat
* @version 1.0
*/
@Data
public class LoginUserModel {

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    @TableField("username")
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    @TableField("password")
    private String password;

}
