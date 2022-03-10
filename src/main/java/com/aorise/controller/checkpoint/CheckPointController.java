package com.aorise.controller.checkpoint;

import com.aorise.model.checkpoint.CheckPointEntity;
import com.aorise.model.checkpoint.CheckPointRecordEntity;
import com.aorise.model.scenic.ScenicEntity;
import com.aorise.service.checkpoint.CheckPointRecordService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 打卡管理 控制器
 *
 * @author cat
 * @version 1.0
 */
@RestController
@Api(value = "打卡管理", tags = "打卡管理")
public class CheckPointController {

    //日志打印器
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CheckPointService checkPointService;
    @Autowired
    CheckPointRecordService checkPointRecordService;

    /**
     * 查询所有打卡点信息
     * HTTP 方式：GET
     * API 路径：/api/checkPoint/getAllCheckPoint
     * 方法名：getAllCheckPoint
     * 方法返回类型：String
     */
    @ApiOperation(value = "查询所有打卡点信息", notes = "查询所有打卡点信息", produces = "application/json")
    @RequestMapping(value = "/api/checkPoint/getAllCheckPoint", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getAllCheckPoint() {
        logger.debug("Request RESTful API:getAllCheckPoint");
        List<CheckPointEntity> checkPointEntities = null;
        try {
            QueryWrapper<CheckPointEntity> checkPointEntityQueryWrapper = new QueryWrapper<>();
            checkPointEntityQueryWrapper.eq("is_delete", ConstDefine.IS_NOT_DELETE);
            checkPointEntities = checkPointService.list(checkPointEntityQueryWrapper);
        } catch (Exception e) {

            return new JsonResponseData(false, StatusDefineMessage.getMessage(StatusDefine.FAILURE), StatusDefine.FAILURE, "", "").toString();
        }
        return new JsonResponseData(true, StatusDefineMessage.getMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS, "", checkPointEntities).toString();
    }

    /**
     * 根据条件分页查询打卡记录
     * HTTP 方式：GET
     * API 路径：/api/checkPoint/checkPointRecord/pageIndex/{pageIndex}/pageNum/{pageNum}
     * 方法名：getCheckPointRecordByPage
     * 方法返回类型：String
     */
    @ApiOperation(value = "根据条件分页查询打卡记录", notes = "根据条件分页查询打卡记录", produces = "application/json")
    @RequestMapping(value = "/api/checkPoint/checkPointRecord/pageIndex/{pageIndex}/pageNum/{pageNum}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
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

}
