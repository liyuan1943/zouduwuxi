package com.aorise.model.activity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
* 活动景点关联表
* @author cat
* @version 1.0
*/
@TableName("activity_scenic")
@Data
public class ActivityScenicEntity {

    /**
     * 主键
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    /**
     * 活动ID
     */
    @ApiModelProperty(value = "活动ID")
    @TableField("activity_id")
    private Integer activityId;

    /**
     * 景点ID
     */
    @ApiModelProperty(value = "景点ID")
    @TableField("scenic_id")
    private Integer scenicId;

}
