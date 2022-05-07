package com.aorise.model.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author cat
 * @Description
 * @date  Created in 2018/9/12 10:50
 * @modified By:
 */
@TableName("sys_user")
@Data
public class SysUserModel{

    @TableField("id")
    private Integer id;
    @TableField("user_name")
    private String userName;
    @TableField("password")
    private String passWord;
    @TableField("name")
    private String name;
    @TableField("phone_num")
    private String phoneNum;
    @TableField("id_number")
    private String idNumber;
    @TableField("role")
    private Integer role;
    @TableField("level")
    private Integer level;


    @TableField(exist = false)
    private String token;



}
