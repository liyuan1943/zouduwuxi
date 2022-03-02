package com.aorise.model.medal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
* 勋章
* @author cat
* @version 1.0
*/
@TableName("medal")
@Data
public class MedalEntity {

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
    private String scenicId;

    /**
     * 年份
     */
    @ApiModelProperty(value = "年份")
    @TableField("year")
    private String year;

    /**
     * 勋章图片
     */
    @ApiModelProperty(value = "勋章图片")
    @TableField("pic")
    private String pic;

    /**
     * 是否删除：-1删除，1正常
     */
    @TableField("is_delete")
    private Integer isDelete;



}
