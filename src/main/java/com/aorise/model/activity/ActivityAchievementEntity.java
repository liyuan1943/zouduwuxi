package com.aorise.model.activity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
* 活动成就表
* @author cat
* @version 1.0
*/
@TableName("activity_achievement")
@Data
public class ActivityAchievementEntity {

    /**
     * 主键
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    /**
     * 会员ID
     */
    @ApiModelProperty(value = "会员ID")
    @TableField("member_id")
    private Integer memberId;

    /**
     * 活动ID
     */
    @ApiModelProperty(value = "活动ID")
    @TableField("activity_id")
    private Integer activityId;

    /**
     * 创建时间
     */
    @TableField("create_date")
    private String createDate;




}
