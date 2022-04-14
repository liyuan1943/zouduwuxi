package com.aorise.controller.system;

import com.aorise.model.system.ConfigEntity;
import com.aorise.model.system.QuestionEntity;
import com.aorise.service.system.ConfigService;
import com.aorise.service.system.QuestionService;
import com.aorise.utils.StatusDefine;
import com.aorise.utils.StatusDefineMessage;
import com.aorise.utils.json.JsonResponseData;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.License;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 常见问题 控制器
 *
 * @author cat
 * @version 1.0
 */
@RestController
@Api(value = "常见问题模块", tags = "常见问题模块")
public class QuestionController {

    //日志打印器
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    QuestionService questionService;

    /**
     * 查询所有问题
     * HTTP 方式：GET
     * API 路径：/api/config
     * 方法名：getQuestion
     * 方法返回类型：String
     */
    @ApiOperation(value = "查询所有问题", notes = "查询所有问题", produces = "application/json")
    @RequestMapping(value = "/api/question", method = RequestMethod.GET)
    public String getQuestion() {
        logger.debug("Request RESTful API:getQuestion");

        //装载查询条件
        QueryWrapper<QuestionEntity> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("id");
        List<QuestionEntity> questionEntities = questionService.list(wrapper);

        return new JsonResponseData(true, StatusDefineMessage.getMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS, "", questionEntities).toString();
    }


}
