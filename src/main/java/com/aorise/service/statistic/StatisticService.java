package com.aorise.service.statistic;


import com.aorise.controller.statistic.vo.ScenicRankVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;

/**
* 数据统计 Service层
* @author cat
* @version 1.0
*/
public interface StatisticService {

    /**
     * 景点排行榜
     *
     * @param map  查询条件
     * @return List 景点排行榜信息集合
     * @author yulu
     * @date 2020-08-14
     */
    List<ScenicRankVo> getScenicRank(Map<String, Object> map);

    /**
     * 活动排行榜
     *
     * @param map 查询条件
     * @return List 活动排行榜信息集合
     * @author yulu
     * @date 2020-08-14
     */
    List<ScenicRankVo> getActivityRank(Map<String, Object> map);
}
