package com.aorise.model.scenic;

import com.aorise.model.checkpoint.CheckPointEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
* 景点路线表
* @author cat
* @version 1.0
*/
@TableName("route")
@Data
public class RouteEntity {

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
     * 路线名称
     */
    @ApiModelProperty(value = "路线名称")
    @TableField("name")
    private String name;

    /**
     * 图片
     */
    @ApiModelProperty(value = "图片")
    @TableField("pic")
    private String pic;

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
     * 打卡点集合
     */
    @TableField(exist = false)
    private List<CheckPointEntity> checkPointEntities;

}
