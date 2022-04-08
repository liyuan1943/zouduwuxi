package com.aorise.service.statistic.impl;

import com.aorise.controller.statistic.vo.ScenicRankVo;
import com.aorise.mapper.scenic.ScenicAchievementMapper;
import com.aorise.model.checkpoint.CheckPointEntity;
import com.aorise.model.checkpoint.CheckPointRecordEntity;
import com.aorise.service.statistic.StatisticService;
import com.aorise.utils.define.ConstDefine;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
* 数据统计 ServiceImpl层
* @author cat
* @version 1.0
*/
@Service
@Transactional(rollbackFor = {Exception.class})
public class StatisticServiceImpl implements StatisticService {

    @Autowired
    ScenicAchievementMapper scenicAchievementMapper;

    /**
     * 景点排行榜
     *
     * @param map  查询条件
     * @param page 分页
     * @return Page 景点排行榜信息集合
     * @author yulu
     * @date 2020-08-14
     */
    @Override
    public Page<ScenicRankVo> getScenicRankByPage(Map<String, Object> map, Page<ScenicRankVo> page) {
        Integer memberId = map.get("memberId") == null ? null : Integer.parseInt(map.get("memberId").toString());
        Integer scenicId = map.get("scenicId") == null ? null : Integer.parseInt(map.get("scenicId").toString());
        Integer timeType = map.get("timeType") == null ? null : Integer.parseInt(map.get("timeType").toString());

//        //装载查询条件
//        QueryWrapper<CheckPointRecordEntity> queryWrapper = new QueryWrapper<>();
//        if (memberId != null) {
//            queryWrapper.eq("member_id", memberId);
//        }
//        if (scenicId != null) {
//            queryWrapper.eq("scenic_id", scenicId);
//        }
//        if (timeType != null) {
//            queryWrapper.eq("scenic_id", scenicId);
//        }
//
//        queryWrapper.eq("is_delete", ConstDefine.IS_NOT_DELETE);
//        queryWrapper.orderByDesc("create_date");
//        page = this.page(page, queryWrapper);
//
//        //查询打卡点名称
//        List<CheckPointRecordEntity> entities = page.getRecords();
//        if (entities != null) {
//            for (CheckPointRecordEntity checkPointRecordEntity : entities) {
//                CheckPointEntity checkPointEntity = checkPointService.getById(checkPointRecordEntity.getCheckPointId());
//                if (checkPointEntity != null) {
//                    checkPointRecordEntity.setCheckPointName(checkPointEntity.getName());
//                }
//            }
//        }
        return null;
    }
}
