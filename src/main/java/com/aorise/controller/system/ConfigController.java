package com.aorise.controller.system;

import com.aorise.model.system.ConfigEntity;
import com.aorise.service.system.ConfigService;
import com.aorise.utils.StatusDefine;
import com.aorise.utils.StatusDefineMessage;
import com.aorise.utils.json.JsonResponseData;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 系统信息配置 控制器
 *
 * @author cat
 * @version 1.0
 */
@RestController
@Api(value = "系统信息配置模块", tags = "系统信息配置模块")
public class ConfigController {

    //日志打印器
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 系统信息配置服务接口
     */
    @Autowired
    ConfigService configService;

    /**
     * 查询系统信息配置信息
     * HTTP 方式：GET
     * API 路径：/api/config
     * 方法名：getConfig
     * 方法返回类型：String
     */
    @ApiOperation(value = "查询系统信息配置信息", notes = "查询系统信息配置信息", produces = "application/json")
    @RequestMapping(value = "/api/config", method = RequestMethod.GET)
    public String getConfig() {
        logger.debug("Request RESTful API:getConfigByPage");

        ConfigEntity configEntity = configService.getById(1);

        return new JsonResponseData(true, StatusDefineMessage.getMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS, "", configEntity).toString();
    }

    /**
     * 修改系统信息配置信息
     * HTTP 方式：POST
     * API 路径：/api/config/updateConfig
     * 方法名：updateConfig
     * 方法返回类型：String
     */
    @ApiOperation(value = "修改系统信息配置信息", notes = "修改系统信息配置信息", produces = "application/json")
    @RequestMapping(value = "/api/config/updateConfig", method = RequestMethod.POST)
    public String updateConfig(@RequestBody @Validated ConfigEntity configEntity, HttpServletRequest request) {
        logger.debug("Request RESTful API:updateConfig");
        logger.debug("config：" + configEntity);

        boolean bol = configService.updateById(configEntity);
        if (bol) {
            return new JsonResponseData(true, StatusDefineMessage.getMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS, "", "").toString();
        } else {
            return new JsonResponseData(false, StatusDefineMessage.getMessage(StatusDefine.FAILURE), StatusDefine.FAILURE, "", "").toString();
        }
    }


}
