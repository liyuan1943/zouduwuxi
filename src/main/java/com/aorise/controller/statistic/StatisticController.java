package com.aorise.controller.statistic;

import com.aorise.controller.statistic.vo.ScenicRankVo;
import com.aorise.service.statistic.StatisticService;
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
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据统计 控制器
 *
 * @author cat
 * @version 1.0
 */
@RestController
@Api(value = "数据统计", tags = "数据统计")
public class StatisticController {

    //日志打印器
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    StatisticService statisticService;

    /**
     * 景点排行榜
     * HTTP 方式：GET
     * API 路径：/api/statistic/getScenicRank
     * 方法名：getScenicRankByPage
     * 方法返回类型：String
     */
    @ApiOperation(value = "景点排行榜", notes = "景点排行榜", produces = "application/json")
    @RequestMapping(value = "/api/statistic/getScenicRank", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getScenicRank(@ApiParam(value = "会员ID", required = true) @RequestParam(value = "memberId", required = true) Integer memberId,
                                @ApiParam(value = "景点ID", required = false) @RequestParam(value = "scenicId", required = false) Integer scenicId,
                                @ApiParam(value = "时间：1总榜，2年榜，3月榜", required = true) @RequestParam(value = "timeType", required = true) Integer timeType) {
        logger.debug("Request RESTful API:getScenicRank");
        logger.debug("memberId：" + memberId);
        logger.debug("scenicId：" + scenicId);
        logger.debug("timeType：" + timeType);
        //封装查询参数
        Map<String, Object> map = new HashMap<>(16);
        map.put("memberId", memberId);
        map.put("scenicId", scenicId);
        map.put("timeType", timeType);
        List<ScenicRankVo> scenicRankVos = statisticService.getScenicRank(map);
        return new JsonResponseData(true, StatusDefineMessage.getMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS, "", scenicRankVos).toString();
    }

    /**
     * 活动排行榜
     * HTTP 方式：GET
     * API 路径：/api/statistic/getActivityRank
     * 方法名：getScenicRankByPage
     * 方法返回类型：String
     */
    @ApiOperation(value = "活动排行榜", notes = "活动排行榜", produces = "application/json")
    @RequestMapping(value = "/api/statistic/getActivityRank", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getActivityRank(@ApiParam(value = "会员ID", required = true) @RequestParam(value = "memberId", required = true) Integer memberId,
                                @ApiParam(value = "活动ID", required = true) @RequestParam(value = "activityId", required = true) Integer activityId) {
        logger.debug("Request RESTful API:getScenicRank");
        logger.debug("memberId：" + memberId);
        logger.debug("activityId：" + activityId);
        //封装查询参数
        Map<String, Object> map = new HashMap<>(16);
        map.put("memberId", memberId);
        map.put("activityId", activityId);
        List<ScenicRankVo> scenicRankVos = statisticService.getActivityRank(map);
        return new JsonResponseData(true, StatusDefineMessage.getMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS, "", scenicRankVos).toString();
    }

}
