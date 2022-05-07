package com.aorise.service.statistic.impl;

import com.aorise.controller.statistic.vo.ScenicRankVo;
import com.aorise.exceptions.ServiceException;
import com.aorise.mapper.activity.ActivityAchievementMapper;
import com.aorise.mapper.activity.ActivityScenicMapper;
import com.aorise.mapper.scenic.ScenicAchievementMapper;
import com.aorise.model.activity.ActivityAchievementEntity;
import com.aorise.model.activity.ActivityScenicEntity;
import com.aorise.model.member.MemberEntity;
import com.aorise.model.scenic.ScenicAchievementEntity;
import com.aorise.service.member.MemberService;
import com.aorise.service.statistic.StatisticService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 数据统计 ServiceImpl层
 *
 * @author cat
 * @version 1.0
 */
@Service
@Transactional(rollbackFor = {Exception.class})
public class StatisticServiceImpl implements StatisticService {

    @Autowired
    ScenicAchievementMapper scenicAchievementMapper;
    @Autowired
    ActivityAchievementMapper activityAchievementMapper;
    @Autowired
    MemberService memberService;
    @Autowired
    ActivityScenicMapper activityScenicMapper;

    /**
     * 景点排行榜
     *
     * @param map 查询条件
     * @return List 景点排行榜信息集合
     * @author yulu
     * @date 2020-08-14
     */
    @Override
    public List<ScenicRankVo> getScenicRank(Map<String, Object> map) {
        Integer memberId = map.get("memberId") == null ? null : Integer.parseInt(map.get("memberId").toString());
        Integer scenicId = map.get("scenicId") == null ? null : Integer.parseInt(map.get("scenicId").toString());
        Integer timeType = map.get("timeType") == null ? null : Integer.parseInt(map.get("timeType").toString());

        if (timeType == null) {
            throw new ServiceException("缺少时间参数");
        }

        MemberEntity member = memberService.getById(memberId);
        if (member == null) {
            throw new ServiceException("我的会员信息不存在");
        }
        //装载查询条件
        QueryWrapper<ScenicAchievementEntity> queryWrapper = new QueryWrapper<>();
        if (scenicId != null) {
            queryWrapper.eq("scenic_id", scenicId);
        }
        Calendar cal = Calendar.getInstance();
        if (timeType == 2) {
            //年榜
            queryWrapper.eq("YEAR(create_date)", cal.get(Calendar.YEAR));
        } else if (timeType == 3) {
            //月榜
            queryWrapper.eq("YEAR(create_date)", cal.get(Calendar.YEAR));
            queryWrapper.eq("month(create_date)", cal.get(Calendar.MONTH) + 1);
        }
        queryWrapper.orderByAsc("create_date");

        List<ScenicAchievementEntity> scenicAchievementEntities = scenicAchievementMapper.selectList(queryWrapper);
        List<ScenicRankVo> scenicRankVos = new ArrayList<>();
        if (scenicAchievementEntities.size() > 0) {
            //统计总打卡次数
            Map<Integer, Long> checkSumMap = scenicAchievementEntities.stream()
                    .collect(Collectors.groupingBy(ScenicAchievementEntity::getMemberId, Collectors.counting()));
            //统计打卡景点数
            Map<Integer, Map<String, Long>> scenicSumMap = scenicAchievementEntities.stream()
                    .collect(Collectors.groupingBy(ScenicAchievementEntity::getMemberId, Collectors.groupingBy(o -> o.getMemberId() + "_" + o.getScenicId(), Collectors.counting())));
            //统计最后打卡时间
            Map<Integer, Optional<ScenicAchievementEntity>> checkDateMap = scenicAchievementEntities.stream()
                    .collect(Collectors.groupingBy(ScenicAchievementEntity::getMemberId, Collectors.maxBy(Comparator.comparing(ScenicAchievementEntity::getCreateDate))));

            //转换成list，并排序
            scenicRankVos = checkSumMap.keySet().stream().map(key -> {

                ScenicRankVo scenicRankVo = new ScenicRankVo();
                scenicRankVo.setMemberId(key);
                scenicRankVo.setCheckSum(checkSumMap.get(key).intValue());
                scenicRankVo.setScenicSum(scenicSumMap.get(key).size());
                scenicRankVo.setCheckTime(checkDateMap.get(key).get().getCreateDate());
                //查询会员信息
                MemberEntity memberEntity = memberService.getById(key);
                if (memberEntity != null) {
                    scenicRankVo.setNickname(memberEntity.getNickname());
                    scenicRankVo.setHeadPic(memberEntity.getHeadPic());
                }
                return scenicRankVo;
            }).sorted(Comparator.comparing(ScenicRankVo::getCheckSum,Comparator.reverseOrder())
                    .thenComparing(ScenicRankVo::getScenicSum,Comparator.reverseOrder())
                    .thenComparing(ScenicRankVo::getCheckTime))
                    .collect(Collectors.toList());

            //我的排行榜数据
            ScenicRankVo myScenicRankVo = new ScenicRankVo();
            myScenicRankVo.setMemberId(memberId);
            //获取我的会员ID在排行榜list中的下标
            int index = -1;
            for (int i = 0; i < scenicRankVos.size(); i++) {
                if (scenicRankVos.get(i).getMemberId().equals(memberId)) {
                    index = i;
                    break;
                }
            }

            if (index == -1) {
                //我的排行榜数据不存在
                myScenicRankVo.setNickname(member.getNickname());
                myScenicRankVo.setHeadPic(member.getHeadPic());

            } else {
                //我的排行榜数据存在
                myScenicRankVo = scenicRankVos.get(index);
                myScenicRankVo.setNo(index + 1);
            }

            //合并我的排行榜数据和数据列表top100
            List<ScenicRankVo> resultScenicRankVos = new ArrayList<>();
            resultScenicRankVos.add(myScenicRankVo);
            resultScenicRankVos.addAll(scenicRankVos.subList(0, Math.min(scenicRankVos.size(), 100)));
            return resultScenicRankVos;
        }
        //没有排行榜数据，只返回我的排行榜
        List<ScenicRankVo> resultScenicRankVos = new ArrayList<>();
        ScenicRankVo myScenicRankVo = new ScenicRankVo();
        myScenicRankVo.setMemberId(memberId);
        myScenicRankVo.setNickname(member.getNickname());
        myScenicRankVo.setHeadPic(member.getHeadPic());
        resultScenicRankVos.add(myScenicRankVo);
        return resultScenicRankVos;
    }

    /**
     * 活动排行榜
     *
     * @param map 查询条件
     * @return List 活动排行榜信息集合
     * @author yulu
     * @date 2020-08-14
     */
    @Override
    public List<ScenicRankVo> getActivityRank(Map<String, Object> map) {
        Integer memberId = map.get("memberId") == null ? null : Integer.parseInt(map.get("memberId").toString());
        Integer activityId = map.get("activityId") == null ? null : Integer.parseInt(map.get("activityId").toString());

        MemberEntity member = memberService.getById(memberId);
        if (member == null) {
            throw new ServiceException("我的会员信息不存在");
        }

        //装载查询条件
        QueryWrapper<ActivityAchievementEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("activity_id", activityId);
        queryWrapper.orderByAsc("create_date");

        List<ActivityAchievementEntity> activityAchievementEntities = activityAchievementMapper.selectList(queryWrapper);
        List<ScenicRankVo> scenicRankVos = new ArrayList<>();
        if (activityAchievementEntities.size() > 0) {
            //统计总打卡次数
            Map<Integer, Long> checkSumMap = activityAchievementEntities.stream()
                    .collect(Collectors.groupingBy(ActivityAchievementEntity::getMemberId, Collectors.counting()));

            //转换成list，并排序
            scenicRankVos = checkSumMap.keySet().stream().map(key -> {

                ScenicRankVo scenicRankVo = new ScenicRankVo();
                scenicRankVo.setMemberId(key);
                scenicRankVo.setCheckSum(checkSumMap.get(key).intValue());
                //查询活动包含景点数
                QueryWrapper<ActivityScenicEntity> activityScenicEntityQueryWrapper = new QueryWrapper<>();
                activityScenicEntityQueryWrapper.eq("activity_id", activityId);
                List<ActivityScenicEntity> activityScenicEntityList = activityScenicMapper.selectList(activityScenicEntityQueryWrapper);
                scenicRankVo.setScenicSum(activityScenicEntityList.size());
                //查询会员信息
                MemberEntity memberEntity = memberService.getById(key);
                if (memberEntity != null) {
                    scenicRankVo.setNickname(memberEntity.getNickname());
                    scenicRankVo.setHeadPic(memberEntity.getHeadPic());
                }
                return scenicRankVo;
            }).sorted(Comparator.comparing(ScenicRankVo::getCheckSum).reversed()).collect(Collectors.toList());

            //我的排行榜数据
            ScenicRankVo myScenicRankVo = new ScenicRankVo();
            myScenicRankVo.setMemberId(memberId);
            //获取我的会员ID在排行榜list中的下标
            int index = -1;
            for (int i = 0; i < scenicRankVos.size(); i++) {
                if (scenicRankVos.get(i).getMemberId().equals(memberId)) {
                    index = i;
                    break;
                }
            }

            if (index == -1) {
                //我的排行榜数据不存在
                myScenicRankVo.setNickname(member.getNickname());
                myScenicRankVo.setHeadPic(member.getHeadPic());

            } else {
                //我的排行榜数据存在
                myScenicRankVo = scenicRankVos.get(index);
                myScenicRankVo.setNo(index + 1);
            }

            //合并我的排行榜数据和数据列表top100
            List<ScenicRankVo> resultScenicRankVos = new ArrayList<>();
            resultScenicRankVos.add(myScenicRankVo);
            resultScenicRankVos.addAll(scenicRankVos.subList(0, Math.min(scenicRankVos.size(), 100)));
            return resultScenicRankVos;
        }
        //没有排行榜数据，只返回我的排行榜
        List<ScenicRankVo> resultScenicRankVos = new ArrayList<>();
        ScenicRankVo myScenicRankVo = new ScenicRankVo();
        myScenicRankVo.setMemberId(memberId);
        myScenicRankVo.setNickname(member.getNickname());
        myScenicRankVo.setHeadPic(member.getHeadPic());
        resultScenicRankVos.add(myScenicRankVo);
        return resultScenicRankVos;
    }
}
