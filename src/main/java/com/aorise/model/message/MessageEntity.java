package com.aorise.model.message;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
* 留言表
* @author cat
* @version 1.0
*/
@TableName("message")
@Data
public class MessageEntity {

    /**
     * 主键
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    /**
     * 景点ID
     */
    @ApiModelProperty(value = "景点ID")
    @TableField("scenic_id")
    private Integer scenicId;

    /**
     * 会员ID
     */
    @ApiModelProperty(value = "会员ID")
    @TableField("member_id")
    private Integer memberId;

    /**
     * 内容
     */
    @ApiModelProperty(value = "内容")
    @TableField("content")
    private String content;

    /**
     * 经度
     */
    @ApiModelProperty(value = "经度")
    @TableField("longitude")
    private String longitude;

    /**
     * 纬度
     */
    @ApiModelProperty(value = "纬度")
    @TableField("latitude")
    private String latitude;

    /**
     * 打卡地点
     */
    @ApiModelProperty(value = "打卡地点")
    @TableField("location")
    private String location;

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

    /**
     * 会员昵称
     */
    @TableField(exist = false)
    private String nickname;

    /**
     * 会员头像
     */
    @TableField(exist = false)
    private String headPic;

    /**
     * 留言图片(逗号分隔)
     */
    @TableField(exist = false)
    private String messagePics;
}
