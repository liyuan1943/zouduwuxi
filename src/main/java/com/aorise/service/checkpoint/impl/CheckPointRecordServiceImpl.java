package com.aorise.service.checkpoint.impl;

import com.aorise.exceptions.ServiceException;
import com.aorise.mapper.activity.ActivityAchievementMapper;
import com.aorise.mapper.activity.ActivityScenicMapper;
import com.aorise.mapper.checkpoint.CheckPointRecordMapper;
import com.aorise.mapper.scenic.RouteCheckPointMapper;
import com.aorise.mapper.scenic.RouteMapper;
import com.aorise.mapper.scenic.ScenicAchievementMapper;
import com.aorise.model.activity.ActivityAchievementEntity;
import com.aorise.model.activity.ActivityEntity;
import com.aorise.model.activity.ActivityScenicEntity;
import com.aorise.model.checkpoint.CheckPointEntity;
import com.aorise.model.checkpoint.CheckPointRecordEntity;
import com.aorise.model.member.MemberEntity;
import com.aorise.model.scenic.RouteCheckPointEntity;
import com.aorise.model.scenic.RouteEntity;
import com.aorise.model.scenic.ScenicAchievementEntity;
import com.aorise.model.scenic.ScenicEntity;
import com.aorise.service.activity.ActivityService;
import com.aorise.service.checkpoint.CheckPointRecordService;
import com.aorise.service.checkpoint.CheckPointService;
import com.aorise.service.member.MemberService;
import com.aorise.service.scenic.ScenicService;
import com.aorise.utils.Utils;
import com.aorise.utils.define.ConstDefine;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


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
    @Autowired
    ScenicService scenicService;
    @Autowired
    MemberService memberService;
    @Autowired
    RouteMapper routeMapper;
    @Autowired
    RouteCheckPointMapper routeCheckPointMapper;

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
        queryWrapper.orderByDesc("check_time");
        page = this.page(page, queryWrapper);

        List<CheckPointRecordEntity> entities = page.getRecords();
        if (entities != null) {
            for (CheckPointRecordEntity checkPointRecordEntity : entities) {
                //查询打卡点名称
                CheckPointEntity checkPointEntity = checkPointService.getById(checkPointRecordEntity.getCheckPointId());
                if (checkPointEntity != null) {
                    checkPointRecordEntity.setCheckPointName(checkPointEntity.getName());
                }
                //查询景点名称
                ScenicEntity scenicEntity = scenicService.getById(checkPointRecordEntity.getScenicId());
                if (scenicEntity != null) {
                    checkPointRecordEntity.setScenicName(scenicEntity.getName());
                }
                //查询会员昵称
                MemberEntity memberEntity = memberService.getById(checkPointRecordEntity.getMemberId());
                if (memberEntity != null) {
                    checkPointRecordEntity.setNickname(memberEntity.getNickname());
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
        //查询该打卡点当天是否有打卡记录
        QueryWrapper<CheckPointRecordEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("check_point_id", checkPointRecordEntity.getCheckPointId());
        queryWrapper.eq("member_id", checkPointRecordEntity.getMemberId());
        queryWrapper.eq("date_format(check_time,'%Y-%m-%d')", Utils.dateToStr(new Date(), "yyyy-MM-dd"));
        queryWrapper.eq("is_delete", ConstDefine.IS_NOT_DELETE);
        CheckPointRecordEntity checkPointRecord = this.getOne(queryWrapper);
        if (checkPointRecord == null) {
            //不存在，新增打卡记录
            checkPointRecordEntity.setCheckTime(Utils.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
            boolean bool = this.save(checkPointRecordEntity);
            if (!bool) {
                throw new ServiceException("打卡失败，请重试");
            }
            //判断是否要新增景点成就表数据
            QueryWrapper<RouteCheckPointEntity> routeCheckPointEntityQueryWrapper = new QueryWrapper<>();
            routeCheckPointEntityQueryWrapper.eq("check_point_id", checkPointRecordEntity.getCheckPointId());
            routeCheckPointEntityQueryWrapper.eq("is_destination", ConstDefine.IS_YES);
            List<RouteCheckPointEntity> routeCheckPointEntities = routeCheckPointMapper.selectList(routeCheckPointEntityQueryWrapper);

            //该打卡点是否是某条路线的终点
            if (routeCheckPointEntities.size() > 0) {
                //查询该会员当天打卡记录
                QueryWrapper<CheckPointRecordEntity> checkPointRecordEntityQueryWrapper = new QueryWrapper<>();
                checkPointRecordEntityQueryWrapper.eq("member_id", checkPointRecordEntity.getMemberId());
                checkPointRecordEntityQueryWrapper.eq("date_format(check_time,'%Y-%m-%d')", Utils.dateToStr(new Date(), "yyyy-MM-dd"));
                checkPointRecordEntityQueryWrapper.eq("is_delete", ConstDefine.IS_NOT_DELETE);
                checkPointRecordEntityQueryWrapper.orderByAsc("check_time");
                List<CheckPointRecordEntity> checkPointRecordEntities = this.list(checkPointRecordEntityQueryWrapper);
                //打卡集合转换成字符串
                String checkPointRecords = checkPointRecordEntities.stream().map(r -> r.getCheckPointId().toString()).collect(Collectors.joining(","));

                //是否完成某条路线的打卡
                boolean bol = false;

                //查询该景点所有路线
                QueryWrapper<RouteEntity> routeEntityQueryWrapper = new QueryWrapper<>();
                routeEntityQueryWrapper.eq("scenic_id", checkPointRecordEntity.getScenicId());
                routeEntityQueryWrapper.eq("is_delete", ConstDefine.IS_NOT_DELETE);
                List<RouteEntity> routeEntities = routeMapper.selectList(routeEntityQueryWrapper);
                if (routeEntities.size() > 0) {
                    for (RouteEntity routeEntity : routeEntities) {
                        //查询该路线需打卡的打卡点集合
                        QueryWrapper<RouteCheckPointEntity> routeCheckPointEntityQuery = new QueryWrapper<>();
                        routeCheckPointEntityQuery.eq("route_id", routeEntity.getId());
                        routeCheckPointEntityQuery.orderByAsc("no");
                        List<RouteCheckPointEntity> routeCheckPointLists = routeCheckPointMapper.selectList(routeCheckPointEntityQuery);
                        //需打卡的打卡点集合转换成字符串
                        String routeCheckPoints = routeCheckPointLists.stream().map(r -> r.getCheckPointId().toString()).collect(Collectors.joining(","));
                        //判断用户已打卡的路线是否包含需要打卡的路线
                        if (checkPointRecords.contains(routeCheckPoints)) {
                            bol = true;
                            break;
                        }
                    }
                }

                //已完成某条路线的打卡，则新增景点成就
                if (bol) {
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
                            scenicAchievementEntityQueryWrapper.eq("is_delete", ConstDefine.IS_NOT_DELETE);
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
            throw new ServiceException("该打卡点今天已有打卡记录。如需打卡请在【我的足迹】中删除原打卡记录");
        }

        return 1;
    }

}
