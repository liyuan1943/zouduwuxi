package com.aorise.model.medal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
* 会员勋章关联表
* @author cat
* @version 1.0
*/
@TableName("member_medal")
@Data
public class MemberMedalEntity {

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
     * 勋章ID
     */
    @ApiModelProperty(value = "勋章ID")
    @TableField("medal_id")
    private Integer medalId;

    /**
     * 创建时间
     */
    @TableField("create_date")
    private String createDate;




}
