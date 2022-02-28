package com.aorise.model.banner;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
* 轮播图
* @author cat
* @version 1.0
*/
@TableName("banner")
@Data
public class BannerEntity {

    /**
     * 主键
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    /**
     * 类型：1活动，2景点
     */
    @ApiModelProperty(value = "类型：1活动，2景点")
    @TableField("type")
    private String type;

    /**
     * 轮播图
     */
    @ApiModelProperty(value = "轮播图")
    @TableField("pic")
    private String pic;

    /**
     * 是否删除：-1删除，1正常
     */
    @TableField("is_delete")
    private Integer isDelete;

    /**
     * 商品或活动名称
     */
    @TableField(exist = false)
    private String name;


}
