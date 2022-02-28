package com.aorise.model.message;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
* 留言图片表
* @author cat
* @version 1.0
*/
@TableName("message_pic")
@Data
public class MessagePicEntity {

    /**
     * 主键
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    /**
     * 留言ID
     */
    @ApiModelProperty(value = "留言ID")
    @TableField("message_id")
    private Integer messageId;

    /**
     * 图片路径
     */
    @ApiModelProperty(value = "图片路径")
    @TableField("pic")
    private String pic;

    /**
     * 图片排序
     */
    @ApiModelProperty(value = "图片排序")
    @TableField("sort")
    private Integer sort;


}
