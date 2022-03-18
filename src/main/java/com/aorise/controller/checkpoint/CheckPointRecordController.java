package com.aorise.controller.checkpoint;

import com.aorise.model.checkpoint.CheckPointRecordEntity;
import com.aorise.service.checkpoint.CheckPointRecordService;
import com.aorise.utils.StatusDefine;
import com.aorise.utils.StatusDefineMessage;
import com.aorise.utils.json.JsonResponseData;
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

import java.util.HashMap;
import java.util.Map;

/**
 * 打卡记录管理 控制器
 *
 * @author cat
 * @version 1.0
 */
@RestController
@Api(value = "打卡记录管理", tags = "打卡记录管理")
public class CheckPointRecordController {

    //日志打印器
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CheckPointRecordService checkPointRecordService;

    /**
     * 根据条件分页查询打卡记录
     * HTTP 方式：GET
     * API 路径：/api/checkPointRecord/pageIndex/{pageIndex}/pageNum/{pageNum}
     * 方法名：getCheckPointRecordByPage
     * 方法返回类型：String
     */
    @ApiOperation(value = "根据条件分页查询打卡记录", notes = "根据条件分页查询打卡记录", produces = "application/json")
    @RequestMapping(value = "/api/checkPointRecord/pageIndex/{pageIndex}/pageNum/{pageNum}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getCheckPointRecordByPage(@ApiParam(value = "会员ID", required = false) @RequestParam(value = "memberId", required = false) Integer memberId,
                                            @ApiParam(value = "景点ID", required = false) @RequestParam(value = "scenicId", required = false) Integer scenicId,
                                            @ApiParam(value = "页索引", required = true) @PathVariable(value = "pageIndex", required = true) Integer pageIndex,
                                            @ApiParam(value = "页大小", required = true) @PathVariable(value = "pageNum", required = true) Integer pageNum) {
        logger.debug("Request RESTful API:getCheckPointRecordByPage");
        logger.debug("memberId：" + memberId);
        logger.debug("scenicId：" + scenicId);
        logger.debug("pageIndex：" + pageIndex);
        logger.debug("pageNum：" + pageNum);
        Page<CheckPointRecordEntity> page = new Page<>(pageIndex, pageNum);
        try {
            //封装查询参数
            Map<String, Object> map = new HashMap<>(16);
            map.put("memberId", memberId);
            map.put("scenicId", scenicId);
            page = checkPointRecordService.getCheckPointRecordByPage(map, page);

        } catch (Exception e) {

            return new JsonResponseData(false, StatusDefineMessage.getMessage(StatusDefine.FAILURE), StatusDefine.FAILURE, "", "").toString();
        }
        return new JsonResponseData(true, StatusDefineMessage.getMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS, "", page).toString();
    }

    /**
     * 新增打卡记录
     * HTTP 方式：POST
     * API 路径：/api/checkPointRecord/addCheckPointRecord
     * 方法名：addScenic
     * 方法返回类型：String
     */
    @ApiOperation(value = "新增打卡记录", notes = "新增打卡记录", produces = "application/json")
    @RequestMapping(value = "/api/scenic/addCheckPointRecord", method = RequestMethod.POST)
    public String addCheckPointRecord(@RequestBody @Validated CheckPointRecordEntity checkPointRecordEntity) {
        logger.debug("Request RESTful API:addCheckPointRecord");
        logger.debug("checkPointRecordEntity：" + checkPointRecordEntity);

        int iRet = checkPointRecordService.addCheckPointRecord(checkPointRecordEntity);
        if (iRet > 0) {
            return new JsonResponseData(true, StatusDefineMessage.getMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS, "", checkPointRecordEntity.getId()).toString();
        } else {
            return new JsonResponseData(false, StatusDefineMessage.getMessage(StatusDefine.FAILURE), StatusDefine.FAILURE, "", "").toString();
        }
    }

}
