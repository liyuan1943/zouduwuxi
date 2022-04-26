package com.aorise.controller.scenic;

import com.aorise.model.checkpoint.CheckPointEntity;
import com.aorise.model.message.MessageEntity;
import com.aorise.model.scenic.ScenicEntity;
import com.aorise.service.checkpoint.CheckPointService;
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
 * 景点 控制器
 *
 * @author cat
 * @version 1.0
 */
@RestController
@Api(value = "景点模块", tags = "景点模块")
public class ScenicController {

    //日志打印器
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 景点服务接口
     */
    @Autowired
    ScenicService scenicService;

    /**
     * 分页查询景点信息
     * HTTP 方式：GET
     * API 路径：/api/scenic/pageIndex/{pageIndex}/pageNum/{pageNum}
     * 方法名：getScenicByPage
     * 方法返回类型：String
     */
    @ApiOperation(value = "分页查询景点信息", notes = "分页查询景点信息", produces = "application/json")
    @RequestMapping(value = "/api/scenic/pageIndex/{pageIndex}/pageNum/{pageNum}", method = RequestMethod.GET)
    public String getScenicByPage(@ApiParam(value = "景点名称", required = false) @RequestParam(value = "name", required = false) String name,
                                  @ApiParam(value = "活动ID", required = false) @RequestParam(value = "activityId", required = false) Integer activityId,
                                  @ApiParam(value = "页索引", required = true) @PathVariable(value = "pageIndex", required = true) Integer pageIndex,
                                  @ApiParam(value = "页大小", required = true) @PathVariable(value = "pageNum", required = true) Integer pageNum) {
        logger.debug("Request RESTful API:getScenicByPage");
        logger.debug("pageIndex：" + pageIndex);
        logger.debug("pageNum：" + pageNum);

        Page<ScenicEntity> page = new Page<>(pageIndex, pageNum);

        //装载查询条件
        QueryWrapper<ScenicEntity> entity = new QueryWrapper<>();
        if (StringUtils.isNotBlank(name)) {
            entity.like("name", name);
        }
        if (activityId != null && activityId > 0) {
            entity.eq("activity_id", activityId);
        }
        entity.eq("is_delete", ConstDefine.IS_NOT_DELETE);
        entity.orderByDesc("create_date");
        page = scenicService.page(page, entity);

        page = scenicService.getScenicByPage(page, entity);


        return new JsonResponseData(true, StatusDefineMessage.getMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS, "", page).toString();
    }

    /**
     * 根据ID查询景点信息
     * HTTP 方式：GET
     * API 路径：/api/scenic/id/{id}
     * 方法名：getScenicById
     * 方法返回类型：String
     */
    @ApiOperation(value = "根据ID查询景点信息", notes = "根据ID查询景点信息", produces = "application/json")
    @RequestMapping(value = "/api/scenic/id/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getScenicById(@ApiParam(value = "主键ID", required = true) @PathVariable(value = "id", required = true) Integer id) {
        logger.debug("Request RESTful API:getScenicById");
        logger.debug("id：" + id);
        ScenicEntity scenicEntity = null;

        scenicEntity = scenicService.getScenicById(id);

        return new JsonResponseData(true, StatusDefineMessage.getMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS, "", scenicEntity).toString();
    }

    /**
     * 新增景点信息
     * HTTP 方式：POST
     * API 路径：/api/scenic/addScenic
     * 方法名：addScenic
     * 方法返回类型：String
     */
    @ApiOperation(value = "新增景点信息", notes = "新增景点信息", produces = "application/json")
    @RequestMapping(value = "/api/scenic/addScenic", method = RequestMethod.POST)
    public String addScenic(@RequestBody @Validated ScenicEntity scenicEntity) {
        logger.debug("Request RESTful API:addScenic");
        logger.debug("scenic：" + scenicEntity);

        int iRet = scenicService.addScenic(scenicEntity);
        if (iRet > 0) {
            return new JsonResponseData(true, StatusDefineMessage.getMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS, "", scenicEntity.getId()).toString();
        } else {
            return new JsonResponseData(false, StatusDefineMessage.getMessage(StatusDefine.FAILURE), StatusDefine.FAILURE, "", "").toString();
        }
    }

    /**
     * 修改景点信息
     * HTTP 方式：POST
     * API 路径：/api/scenic/updateScenic
     * 方法名：updateScenic
     * 方法返回类型：String
     */
    @ApiOperation(value = "修改景点信息", notes = "修改景点信息", produces = "application/json")
    @RequestMapping(value = "/api/scenic/updateScenic", method = RequestMethod.POST)
    public String updateScenic(@RequestBody @Validated ScenicEntity scenicEntity, HttpServletRequest request) {
        logger.debug("Request RESTful API:updateScenic");
        logger.debug("scenic：" + scenicEntity);

        int iRet = scenicService.updateScenic(scenicEntity, request);
        if (iRet > 0) {
            return new JsonResponseData(true, StatusDefineMessage.getMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS, "", "").toString();
        } else {
            return new JsonResponseData(false, StatusDefineMessage.getMessage(StatusDefine.FAILURE), StatusDefine.FAILURE, "", "").toString();
        }

    }

    /**
     * 删除景点信息
     * HTTP 方式：POST
     * API 路径：/api/scenic/id/{id}
     * 方法名：deleteScenic
     * 方法返回类型：String
     */
    @ApiOperation(value = "删除景点信息", notes = "删除景点信息", produces = "application/json")
    @RequestMapping(value = "/api/scenic/id/{id}", method = RequestMethod.POST)
    public String deleteScenic(@ApiParam(value = "主键ID", required = true) @PathVariable(value = "id", required = true) Integer id,
                               HttpServletRequest request) {
        logger.debug("Request RESTful API:deleteScenic");
        logger.debug("id：" + id);

        int iRet = scenicService.deleteScenic(id, request);
        if (iRet > 0) {
            return new JsonResponseData(true, StatusDefineMessage.getMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS, "", "").toString();
        } else {
            return new JsonResponseData(false, StatusDefineMessage.getMessage(StatusDefine.FAILURE), StatusDefine.FAILURE, "", "").toString();
        }

    }

}
