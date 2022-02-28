package com.aorise.model.member;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

/**
 * @author cat
 * @Description
 * @date  Created in 2018/9/12 10:50
 * @modified By:
 */
@TableName("member")
@Data
public class MemberEntity implements Serializable{
    /**
     * 主键
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    /**
     * 微信用户唯一标识ID
     */
    @TableField("unionid")
    private String unionid;

    /**
     * 公众号openid
     */
    @TableField("openid_pub")
    private String openidPub;

    /**
     * 小程序openid
     */
    @TableField("openid_pro")
    private String openidPro;

    /**
     * 网站openid
     */
    @TableField("openid_web")
    private String openidWeb;

    /**
     * 会员编号
     */
    @TableField("code")
    private String code;

    /**
     * 会员姓名
     */
    @TableField("name")
    private String name;

    /**
     * 手机号码
     */
    @TableField("phone_num")
    private String phoneNum;

    /**
     * 身份证号码
     */
    @TableField("id_number")
    private String idNumber;

    /**
     * 家庭住址
     */
    @TableField("address")
    private String address;

    /**
     * 紧急联系人名字
     */
    @TableField("emerg_person")
    private String emergPerson;

    /**
     * 紧急联系人号码
     */
    @TableField("emerg_phone")
    private String emergPhone;

    /**
     * 出生日期
     */
    @TableField("birthdate")
    private String birthdate;

    /**
     * 性别，1男；2女；
     */
    @TableField("gender")
    private int gender;

    /**
     * 昵称
     */
    @TableField("nickname")
    private String nickname;

    /**
     * 头像图片地址
     */
    @TableField("head_pic")
    private String headPic;

    /**
     * 会员积分
     */
    @TableField("points")
    private Integer points;

    /**
     * 是否删除：-1删除，1正常
     */
    @TableField("is_delete")
    private Integer isDelete;

    /**
     * 创建时间
     */
    @TableField("create_date")
    private String createDate;

    /**
     * 修改日期
     */
    @TableField("edit_date")
    private String editDate;

}
