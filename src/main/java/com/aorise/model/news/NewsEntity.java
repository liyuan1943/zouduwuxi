package com.aorise.model.news;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
* 公告
* @author cat
* @version 1.0
*/
@TableName("news")
@Data
public class NewsEntity {

    /**
     * 主键
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    @TableField("title")
    private String title;

    /**
     * 大图
     */
    @ApiModelProperty(value = "大图")
    @TableField("pic")
    private String pic;

    /**
     * 内容
     */
    @ApiModelProperty(value = "内容")
    @TableField("content")
    private String content;

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
