package com.aorise.model.medal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 勋章
 *
 * @author cat
 * @version 1.0
 */
@TableName("medal")
@Data
public class MedalEntity {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 勋章名称
     */
    @ApiModelProperty(value = "勋章名称")
    @TableField("name")
    private String name;

    /**
     * 关联景点ID（多个逗号分隔）
     */
    @ApiModelProperty(value = "关联景点ID（多个逗号分隔）")
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
     * 是否年份勋章：1是，2否
     */
    @ApiModelProperty(value = "是否年份勋章：1是，2否")
    @TableField("is_year")
    private Integer isYear;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
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
     * 修改日期
     */
    @TableField("edit_date")
    private String editDate;

    /**
     * 是否获得：1是，2否
     */
    @ApiModelProperty(value = "是否获得：1是，2否")
    @TableField(exist = false)
    private Integer isGet;
}
