package com.aorise.mapper.activity;

import com.aorise.model.activity.ActivityEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
* 活动 DAO接口
* @author cat
* @version 1.0
*/
@Mapper
@Component(value = "activityMapper")
public interface ActivityMapper extends BaseMapper<ActivityEntity> {

}
