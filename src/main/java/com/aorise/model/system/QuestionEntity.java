package com.aorise.model.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
* 常见问题
* @author cat
* @version 1.0
*/
@TableName("question")
@Data
public class QuestionEntity {

    /**
     * 主键ID
     */
    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    /**
     * 问题
     */
    @ApiModelProperty(value = "问题")
    @TableField("question")
    private String question;

    /**
     * 答案
     */
    @ApiModelProperty(value = "答案")
    @TableField("answer")
    private String answer;



}
