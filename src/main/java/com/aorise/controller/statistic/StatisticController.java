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
     * API 路径：/api/statistic/pageIndex/{pageIndex}/pageNum/{pageNum}
     * 方法名：getScenicRankByPage
     * 方法返回类型：String
     */
    @ApiOperation(value = "景点排行榜", notes = "景点排行榜", produces = "application/json")
    @RequestMapping(value = "/api/statistic/pageIndex/{pageIndex}/pageNum/{pageNum}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getScenicRankByPage(@ApiParam(value = "会员ID", required = false) @RequestParam(value = "memberId", required = false) Integer memberId,
                                      @ApiParam(value = "景点ID", required = false) @RequestParam(value = "scenicId", required = false) Integer scenicId,
                                      @ApiParam(value = "时间：1总榜，2年榜，3月榜", required = false) @RequestParam(value = "timeType", required = false) Integer timeType,
                                      @ApiParam(value = "页索引", required = true) @PathVariable(value = "pageIndex", required = true) Integer pageIndex,
                                      @ApiParam(value = "页大小", required = true) @PathVariable(value = "pageNum", required = true) Integer pageNum) {
        logger.debug("Request RESTful API:getScenicRankByPage");
        logger.debug("memberId：" + memberId);
        logger.debug("scenicId：" + scenicId);
        logger.debug("timeType：" + timeType);
        logger.debug("pageIndex：" + pageIndex);
        logger.debug("pageNum：" + pageNum);
        Page<ScenicRankVo> page = new Page<>(pageIndex, pageNum);
        //封装查询参数
        Map<String, Object> map = new HashMap<>(16);
        map.put("memberId", memberId);
        map.put("scenicId", scenicId);
        map.put("memberId", timeType);
        page = statisticService.getScenicRankByPage(map, page);
        return new JsonResponseData(true, StatusDefineMessage.getMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS, "", page).toString();
    }

}
