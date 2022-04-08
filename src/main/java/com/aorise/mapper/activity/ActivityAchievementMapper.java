package com.aorise.mapper.activity;

import com.aorise.model.activity.ActivityAchievementEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
* 活动成就表 DAO接口
* @author cat
* @version 1.0
*/
@Mapper
@Component(value = "activityAchievementMapper")
public interface ActivityAchievementMapper extends BaseMapper<ActivityAchievementEntity> {

}
