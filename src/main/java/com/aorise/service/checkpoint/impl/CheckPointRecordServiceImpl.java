package com.aorise.service.checkpoint.impl;

import com.aorise.exceptions.ServiceException;
import com.aorise.mapper.activity.ActivityAchievementMapper;
import com.aorise.mapper.activity.ActivityScenicMapper;
import com.aorise.mapper.checkpoint.CheckPointRecordMapper;
import com.aorise.mapper.scenic.ScenicAchievementMapper;
import com.aorise.model.activity.ActivityAchievementEntity;
import com.aorise.model.activity.ActivityEntity;
import com.aorise.model.activity.ActivityScenicEntity;
import com.aorise.model.checkpoint.CheckPointEntity;
import com.aorise.model.checkpoint.CheckPointRecordEntity;
import com.aorise.model.scenic.ScenicAchievementEntity;
import com.aorise.service.activity.ActivityService;
import com.aorise.service.checkpoint.CheckPointRecordService;
import com.aorise.service.checkpoint.CheckPointService;
import com.aorise.utils.Utils;
import com.aorise.utils.define.ConstDefine;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 打卡记录 ServiceImpl层
 *
 * @author cat
 * @version 1.0
 */
@Service
@Transactional(rollbackFor = {Exception.class})
public class CheckPointRecordServiceImpl extends ServiceImpl<CheckPointRecordMapper, CheckPointRecordEntity> implements CheckPointRecordService {

    @Autowired
    CheckPointService checkPointService;
    @Autowired
    ScenicAchievementMapper scenicAchievementMapper;
    @Autowired
    ActivityAchievementMapper activityAchievementMapper;
    @Autowired
    ActivityScenicMapper activityScenicMapper;
    @Autowired
    ActivityService activityService;

    /**
     * 根据条件分页查询打卡记录
     *
     * @param map  查询条件
     * @param page 分页
     * @return Page 打卡记录信息集合
     * @author yulu
     * @date 2020-08-14
     */
    @Override
    public Page<CheckPointRecordEntity> getCheckPointRecordByPage(Map<String, Object> map, Page<CheckPointRecordEntity> page) {
        Integer memberId = map.get("memberId") == null ? null : Integer.parseInt(map.get("memberId").toString());
        Integer scenicId = map.get("scenicId") == null ? null : Integer.parseInt(map.get("scenicId").toString());

        //装载查询条件
        QueryWrapper<CheckPointRecordEntity> queryWrapper = new QueryWrapper<>();
        if (memberId != null) {
            queryWrapper.eq("member_id", memberId);
        }
        if (scenicId != null) {
            queryWrapper.eq("scenic_id", scenicId);
        }

        queryWrapper.eq("is_delete", ConstDefine.IS_NOT_DELETE);
        queryWrapper.orderByDesc("create_date");
        page = this.page(page, queryWrapper);

        //查询打卡点名称
        List<CheckPointRecordEntity> entities = page.getRecords();
        if (entities != null) {
            for (CheckPointRecordEntity checkPointRecordEntity : entities) {
                CheckPointEntity checkPointEntity = checkPointService.getById(checkPointRecordEntity.getCheckPointId());
                if (checkPointEntity != null) {
                    checkPointRecordEntity.setCheckPointName(checkPointEntity.getName());
                }
            }
        }
        return page;
    }

    /**
     * 新增打卡记录
     *
     * @param checkPointRecordEntity 打卡记录
     * @return int 影响行数
     * @author cat
     * @date Created in 2018/9/20 9:27
     * @modified By:
     */
    @Override
    public int addCheckPointRecord(CheckPointRecordEntity checkPointRecordEntity) {
        boolean bool;
        //查询该打卡点当天是否有记录
        QueryWrapper<CheckPointRecordEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("scenic_id", checkPointRecordEntity.getScenicId());
        queryWrapper.eq("check_point_id", checkPointRecordEntity.getCheckPointId());
        queryWrapper.eq("member_id", checkPointRecordEntity.getMemberId());
        queryWrapper.eq("date_format(check_time,'%Y-%m-%d')", Utils.dateToStr(new Date(), "yyyy-MM-dd"));
        CheckPointRecordEntity checkPointRecord = this.getOne(queryWrapper);
        if (checkPointRecord == null) {
            //不存在，新增打卡记录
            checkPointRecordEntity.setCheckTime(Utils.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
            bool = this.save(checkPointRecordEntity);
            if (bool) {
                //判断是否要新增景点成就表数据
                CheckPointEntity checkPointEntity = checkPointService.getById(checkPointRecordEntity.getCheckPointId());
                //打卡点是终点，则新增景点成就
                if (checkPointEntity.getIsDestination().equals(ConstDefine.IS_DESTINATION_YES)) {
                    ScenicAchievementEntity scenicAchievementEntity = new ScenicAchievementEntity();
                    scenicAchievementEntity.setMemberId(checkPointRecordEntity.getMemberId());
                    scenicAchievementEntity.setScenicId(checkPointRecordEntity.getScenicId());
                    int iRet = scenicAchievementMapper.insert(scenicAchievementEntity);
                    if (iRet <= 0) {
                        throw new ServiceException("新增景点成就失败");
                    }
                    //判断是否要新增活动成就表数据
                    //查询包含这个景点的所有活动
                    QueryWrapper<ActivityScenicEntity> activityScenicEntityQueryWrapper = new QueryWrapper<>();
                    activityScenicEntityQueryWrapper.eq("scenic_id", checkPointRecordEntity.getScenicId());
                    List<ActivityScenicEntity> activityScenicEntitys = activityScenicMapper.selectList(activityScenicEntityQueryWrapper);
                    for (ActivityScenicEntity activityScenicEntity : activityScenicEntitys) {
                        //查询每个活动包括的景点
                        QueryWrapper<ActivityScenicEntity> activityScenicEntityWrapper = new QueryWrapper<>();
                        activityScenicEntityWrapper.eq("activity_id", activityScenicEntity.getActivityId());
                        List<ActivityScenicEntity> activityScenicEntityList = activityScenicMapper.selectList(activityScenicEntityWrapper);
                        //完成景点数
                        int sumScenic = 0;
                        for (ActivityScenicEntity activityScenic : activityScenicEntityList) {
                            //查询活动时间
                            ActivityEntity activity = activityService.getById(activityScenic.getActivityId());
                            //查询每个景点是否都已完成,并在活动时间范围内完成
                            QueryWrapper<ScenicAchievementEntity> scenicAchievementEntityQueryWrapper = new QueryWrapper<>();
                            scenicAchievementEntityQueryWrapper.eq("scenic_id", activityScenic.getScenicId());
                            scenicAchievementEntityQueryWrapper.eq("member_id", checkPointRecordEntity.getMemberId());
                            scenicAchievementEntityQueryWrapper.ge("date_format(create_date,'%Y-%m-%d')", activity.getBeginDate());
                            scenicAchievementEntityQueryWrapper.le("date_format(create_date,'%Y-%m-%d')", activity.getExpirationDate());
                            List<ScenicAchievementEntity> scenicAchievementEntities = scenicAchievementMapper.selectList(scenicAchievementEntityQueryWrapper);
                            if (scenicAchievementEntities.size() > 0) {
                                sumScenic++;
                            }
                        }
                        //如果活动包含的景点全部完成，则活动成就完成
                        if (activityScenicEntityList.size() == sumScenic) {
                            //新增活动成就
                            ActivityAchievementEntity achievementEntity = new ActivityAchievementEntity();
                            achievementEntity.setMemberId(checkPointRecordEntity.getMemberId());
                            achievementEntity.setActivityId(activityScenicEntity.getActivityId());
                            int re = activityAchievementMapper.insert(achievementEntity);
                            if (re <= 0) {
                                throw new ServiceException("新增活动成就失败");
                            }
                        }
                    }

                }
            }
        } else {
            //存在，不能重复打卡
            throw new ServiceException("该打卡点今天已有打卡记录");
        }

        if (bool) {
            return 1;
        } else {
            return 0;
        }
    }

}
