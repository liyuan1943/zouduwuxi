package com.aorise.model.scenic;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
* 景点
* @author cat
* @version 1.0
*/
@TableName("scenic")
@Data
public class ScenicEntity {

    /**
     * 主键
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    /**
     * 景点名称
     */
    @ApiModelProperty(value = "景点名称")
    @TableField("name")
    private String name;

    /**
     * 景点背景图
     */
    @ApiModelProperty(value = "景点背景图")
    @TableField("bgi")
    private String bgi;

    /**
     * 景点主图
     */
    @ApiModelProperty(value = "景点主图")
    @TableField("pic")
    private String pic;

    /**
     * 景点简介
     */
    @ApiModelProperty(value = "景点简介")
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
     * 打卡点集合
     */
    @TableField(exist = false)
    private List<CheckPointEntity> checkPointEntities;

    /**
     * 完成人数
     */
    @TableField(exist = false)
    private Integer finishNum;
}
