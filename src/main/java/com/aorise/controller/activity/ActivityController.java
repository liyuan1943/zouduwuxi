package com.aorise.controller.activity;

import com.aorise.model.activity.ActivityEntity;
import com.aorise.model.scenic.ScenicEntity;
import com.aorise.service.common.UploadService;
import com.aorise.service.activity.ActivityService;
import com.aorise.utils.StatusDefine;
import com.aorise.utils.StatusDefineMessage;
import com.aorise.utils.define.ConstDefine;
import com.aorise.utils.json.JsonResponseData;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 活动 控制器
 *
 * @author cat
 * @version 1.0
 */
@RestController
@Api(value = "活动模块", tags = "活动模块")
public class ActivityController {

    //日志打印器
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 活动服务接口
     */
    @Autowired
    ActivityService activityService;
    @Autowired
    UploadService uploadService;

    /**
     * 分页查询活动信息
     * HTTP 方式：GET
     * API 路径：/api/activity/pageIndex/{pageIndex}/pageNum/{pageNum}
     * 方法名：getActivityByPage
     * 方法返回类型：String
     */
    @ApiOperation(value = "分页查询活动信息", notes = "分页查询活动信息", produces = "application/json")
    @RequestMapping(value = "/api/activity/pageIndex/{pageIndex}/pageNum/{pageNum}", method = RequestMethod.GET)
    public String getActivityByPage(@ApiParam(value = "活动名称", required = false) @RequestParam(value = "name", required = false) String name,
                                    @ApiParam(value = "活动状态：1未开始，2进行中，3已结束", required = false) @RequestParam(value = "isOpenning", required = false) Integer isOpenning,
                                    @ApiParam(value = "页索引", required = true) @PathVariable(value = "pageIndex", required = true) Integer pageIndex,
                                    @ApiParam(value = "页大小", required = true) @PathVariable(value = "pageNum", required = true) Integer pageNum) {
        logger.debug("Request RESTful API:getActivityByPage");
        logger.debug("pageIndex：" + pageIndex);
        logger.debug("pageNum：" + pageNum);

        Page<ActivityEntity> page = new Page<>(pageIndex, pageNum);
        try {
            //封装查询参数
            Map<String, Object> map = new HashMap<>(16);
            map.put("name", name);
            map.put("isOpenning", isOpenning);
            page = activityService.getActivityByPage(map, page);

        } catch (Exception e) {

            return new JsonResponseData(false, StatusDefineMessage.getMessage(StatusDefine.FAILURE), StatusDefine.FAILURE, "", "").toString();
        }
        return new JsonResponseData(true, StatusDefineMessage.getMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS, "", page).toString();
    }

    /**
     * 根据ID查询活动信息
     * HTTP 方式：GET
     * API 路径：/api/activity/id/{id}
     * 方法名：getActivityById
     * 方法返回类型：String
     */
    @ApiOperation(value = "根据ID查询活动信息", notes = "根据ID查询活动信息", produces = "application/json")
    @RequestMapping(value = "/api/activity/id/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getActivityById(@ApiParam(value = "主键ID", required = true) @PathVariable(value = "id", required = true) Integer id) {
        logger.debug("Request RESTful API:getActivityById");
        logger.debug("id：" + id);
        ActivityEntity activityEntity = null;
        try {
            activityEntity = activityService.getActivityById(id);
        } catch (Exception e) {

            return new JsonResponseData(false, StatusDefineMessage.getMessage(StatusDefine.FAILURE), StatusDefine.FAILURE, "", "").toString();
        }
        return new JsonResponseData(true, StatusDefineMessage.getMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS, "", activityEntity).toString();
    }

    /**
     * 新增活动信息
     * HTTP 方式：POST
     * API 路径：/api/activity/addActivity
     * 方法名：addActivity
     * 方法返回类型：String
     */
    @ApiOperation(value = "新增活动信息", notes = "新增活动信息", produces = "application/json")
    @RequestMapping(value = "/api/activity/addActivity", method = RequestMethod.POST)
    public String addActivity(@RequestBody @Validated ActivityEntity activityEntity) {
        logger.debug("Request RESTful API:addActivity");
        logger.debug("activity：" + activityEntity);

        try {
            int iRet = activityService.addActivity(activityEntity);
            if (iRet > 0) {
                return new JsonResponseData(true, StatusDefineMessage.getMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS, "", activityEntity.getId()).toString();
            } else {
                return new JsonResponseData(false, StatusDefineMessage.getMessage(StatusDefine.FAILURE), StatusDefine.FAILURE, "", "").toString();
            }
        } catch (Exception e) {

            return new JsonResponseData(false, StatusDefineMessage.getMessage(StatusDefine.FAILURE), StatusDefine.FAILURE, "", "").toString();
        }
    }

    /**
     * 修改活动信息
     * HTTP 方式：POST
     * API 路径：/api/activity/updateActivity
     * 方法名：updateActivity
     * 方法返回类型：String
     */
    @ApiOperation(value = "修改活动信息", notes = "修改活动信息", produces = "application/json")
    @RequestMapping(value = "/api/activity/updateActivity", method = RequestMethod.POST)
    public String updateActivity(@RequestBody @Validated ActivityEntity activityEntity, HttpServletRequest request) {
        logger.debug("Request RESTful API:updateActivity");
        logger.debug("activity：" + activityEntity);

        try {
            int iRet = activityService.updateActivity(activityEntity, request);
            if (iRet > 0) {
                return new JsonResponseData(true, StatusDefineMessage.getMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS, "", "").toString();
            } else {
                return new JsonResponseData(false, StatusDefineMessage.getMessage(StatusDefine.FAILURE), StatusDefine.FAILURE, "", "").toString();
            }
        } catch (Exception e) {

            return new JsonResponseData(false, StatusDefineMessage.getMessage(StatusDefine.FAILURE), StatusDefine.FAILURE, "", "").toString();
        }
    }

    /**
     * 删除活动信息
     * HTTP 方式：POST
     * API 路径：/api/activity/id/{id}
     * 方法名：deleteActivity
     * 方法返回类型：String
     */
    @ApiOperation(value = "删除活动信息", notes = "删除活动信息", produces = "application/json")
    @RequestMapping(value = "/api/activity/id/{id}", method = RequestMethod.POST)
    public String deleteActivity(@ApiParam(value = "主键ID", required = true) @PathVariable(value = "id", required = true) Integer id,
                                 HttpServletRequest request) {
        logger.debug("Request RESTful API:deleteActivity");
        logger.debug("id：" + id);

        try {
            int iRet = activityService.deleteActivity(id, request);
            if (iRet > 0) {
                return new JsonResponseData(true, StatusDefineMessage.getMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS, "", "").toString();
            } else {
                return new JsonResponseData(false, StatusDefineMessage.getMessage(StatusDefine.FAILURE), StatusDefine.FAILURE, "", "").toString();
            }
        } catch (Exception e) {

            return new JsonResponseData(false, StatusDefineMessage.getMessage(StatusDefine.FAILURE), StatusDefine.FAILURE, "", "").toString();
        }
    }


}
