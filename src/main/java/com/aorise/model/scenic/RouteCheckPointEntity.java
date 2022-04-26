package com.aorise.model.scenic;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
* 路线打卡点关联表
* @author cat
* @version 1.0
*/
@TableName("route_check_point")
@Data
public class RouteCheckPointEntity {

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
     * 路线ID
     */
    @ApiModelProperty(value = "路线ID")
    @TableField("route_id")
    private Integer routeId;

    /**
     * 打卡点ID
     */
    @ApiModelProperty(value = "打卡点ID")
    @TableField("check_point_id")
    private Integer checkPointId;

    /**
     * 打卡点序号
     */
    @ApiModelProperty(value = "打卡点序号")
    @TableField("no")
    private Integer no;

    /**
     * 是否终点：1是，2否
     */
    @ApiModelProperty(value = "是否终点：1是，2否")
    @TableField("is_destination")
    private Integer isDestination;

}
