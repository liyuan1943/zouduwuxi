package com.aorise.model.checkpoint;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
* 景点打卡点
* @author cat
* @version 1.0
*/
@TableName("check_point")
@Data
public class CheckPointEntity {

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
     * 打卡点名称
     */
    @ApiModelProperty(value = "打卡点名称")
    @TableField("name")
    private String name;

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
     * 打卡范围半径
     */
    @ApiModelProperty(value = "打卡范围半径")
    @TableField("radius")
    private Integer radius;

    /**
     * 打卡排序
     */
    @ApiModelProperty(value = "打卡排序")
    @TableField("sort")
    private Integer sort;

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
}
