package com.aorise.controller.scenic;

import com.aorise.model.scenic.RouteEntity;
import com.aorise.model.scenic.ScenicEntity;
import com.aorise.service.scenic.RouteService;
import com.aorise.service.scenic.ScenicService;
import com.aorise.utils.StatusDefine;
import com.aorise.utils.StatusDefineMessage;
import com.aorise.utils.define.ConstDefine;
import com.aorise.utils.json.JsonResponseData;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 景点路线 控制器
 *
 * @author cat
 * @version 1.0
 */
@RestController
@Api(value = "景点路线模块", tags = "景点路线模块")
public class RouteController {

    //日志打印器
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 景点路线服务接口
     */
    @Autowired
    RouteService routeService;

    /**
     * 查询路线列表
     * HTTP 方式：GET
     * API 路径：/api/route
     * 方法名：getRouteList
     * 方法返回类型：String
     */
    @ApiOperation(value = "查询路线列表", notes = "查询路线列表", produces = "application/json")
    @RequestMapping(value = "/api/route", method = RequestMethod.GET)
    public String getRouteList(@ApiParam(value = "景点ID", required = true) @RequestParam(value = "scenicId", required = true) Integer scenicId) {
        logger.debug("Request RESTful API:getRouteList");
        logger.debug("scenicId：" + scenicId);

        List<RouteEntity> routeEntities = routeService.getRouteList(scenicId);

        return new JsonResponseData(true, StatusDefineMessage.getMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS, "", routeEntities).toString();
    }

    /**
     * 新增路线信息
     * HTTP 方式：POST
     * API 路径：/api/route/addRoute
     * 方法名：addRoute
     * 方法返回类型：String
     */
    @ApiOperation(value = "新增路线信息", notes = "新增路线信息", produces = "application/json")
    @RequestMapping(value = "/api/route/addRoute", method = RequestMethod.POST)
    public String addRoute(@RequestBody @Validated RouteEntity routeEntity) {
        logger.debug("Request RESTful API:addRoute");
        logger.debug("routeEntity：" + routeEntity);

        int iRet = routeService.addRoute(routeEntity);
        if (iRet > 0) {
            return new JsonResponseData(true, StatusDefineMessage.getMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS, "", routeEntity.getId()).toString();
        } else {
            return new JsonResponseData(false, StatusDefineMessage.getMessage(StatusDefine.FAILURE), StatusDefine.FAILURE, "", "").toString();
        }
    }

    /**
     * 修改路线信息
     * HTTP 方式：POST
     * API 路径：/api/route/updateRoute
     * 方法名：updateRoute
     * 方法返回类型：String
     */
    @ApiOperation(value = "修改路线信息", notes = "修改路线信息", produces = "application/json")
    @RequestMapping(value = "/api/route/updateRoute", method = RequestMethod.POST)
    public String updateRoute(@RequestBody @Validated RouteEntity routeEntity) {
        logger.debug("Request RESTful API:updateRoute");
        logger.debug("routeEntity：" + routeEntity);

        int iRet = routeService.updateRoute(routeEntity);
        if (iRet > 0) {
            return new JsonResponseData(true, StatusDefineMessage.getMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS, "", "").toString();
        } else {
            return new JsonResponseData(false, StatusDefineMessage.getMessage(StatusDefine.FAILURE), StatusDefine.FAILURE, "", "").toString();
        }

    }

    /**
     * 删除路线信息
     * HTTP 方式：POST
     * API 路径：/api/route/id/{id}
     * 方法名：deleteRoute
     * 方法返回类型：String
     */
    @ApiOperation(value = "删除路线信息", notes = "删除路线信息", produces = "application/json")
    @RequestMapping(value = "/api/route/id/{id}", method = RequestMethod.POST)
    public String deleteRoute(@ApiParam(value = "主键ID", required = true) @PathVariable(value = "id", required = true) Integer id,
                              HttpServletRequest request) {
        logger.debug("Request RESTful API:deleteRoute");
        logger.debug("id：" + id);

        int iRet = routeService.deleteRoute(id, request);
        if (iRet > 0) {
            return new JsonResponseData(true, StatusDefineMessage.getMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS, "", "").toString();
        } else {
            return new JsonResponseData(false, StatusDefineMessage.getMessage(StatusDefine.FAILURE), StatusDefine.FAILURE, "", "").toString();
        }

    }

}
