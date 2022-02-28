package com.aorise.model.activity;

import com.aorise.model.scenic.ScenicEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
* 活动
* @author cat
* @version 1.0
*/
@TableName("activity")
@Data
public class ActivityEntity {

    /**
     * 主键
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    /**
     * 活动名称
     */
    @ApiModelProperty(value = "活动名称")
    @TableField("name")
    private String name;

    /**
     * 活动背景图
     */
    @ApiModelProperty(value = "活动背景图")
    @TableField("bgi")
    private String bgi;

    /**
     * 开始日期
     */
    @ApiModelProperty(value = "开始日期")
    @TableField("begin_date")
    private String beginDate;

    /**
     * 截止日期
     */
    @ApiModelProperty(value = "截止日期")
    @TableField("expiration_date")
    private String expirationDate;

    /**
     * 活动简介
     */
    @ApiModelProperty(value = "活动简介")
    @TableField("intro")
    private String intro;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    @TableField("description")
    private String description;

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
     * 修改时间
     */
    @TableField("edit_date")
    private String editDate;

    /**
     * 关联景点ID(逗号分隔)
     */
    @TableField(exist = false)
    private String scenicIds;

    /**
     * 活动状态：1未开始，2进行中，3已结束
     */
    @TableField(exist = false)
    private Integer isOpenning;

    /**
     * 完成人数
     */
    @TableField(exist = false)
    private Integer finishNum;

    /**
     * 关联景点集合
     */
    @TableField(exist = false)
    private List<ScenicEntity> scenicEntities;
}
