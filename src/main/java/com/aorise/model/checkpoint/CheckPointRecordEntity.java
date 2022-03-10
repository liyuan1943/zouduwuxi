package com.aorise.model.checkpoint;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
* 打卡记录
* @author cat
* @version 1.0
*/
@TableName("check_point_record")
@Data
public class CheckPointRecordEntity {

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
     * 打卡点ID
     */
    @ApiModelProperty(value = "打卡点ID")
    @TableField("check_point_id")
    private Integer checkPointId;

    /**
     * 会员ID
     */
    @ApiModelProperty(value = "会员ID")
    @TableField("member_id")
    private Integer memberId;

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
     * 打卡点名称
     */
    @ApiModelProperty(value = "打卡点名称")
    @TableField(exist = false)
    private String checkPointName;
}
