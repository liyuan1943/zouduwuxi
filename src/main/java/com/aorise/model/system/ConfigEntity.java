package com.aorise.model.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
* 系统信息配置
* @author cat
* @version 1.0
*/
@TableName("config")
@Data
public class ConfigEntity {

    /**
     * 主键ID
     */
    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    /**
     * 救援电话
     */
    @ApiModelProperty(value = "救援电话")
    @TableField("rescue_tel")
    private String rescueTel;

    /**
     * 关于我们
     */
    @ApiModelProperty(value = "关于我们")
    @TableField("introduce")
    private String introduce;



}
