package com.aorise.mapper.activity;

import com.aorise.model.activity.ActivityScenicEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
* 活动景点关联 DAO接口
* @author cat
* @version 1.0
*/
@Mapper
@Component(value = "sctivityScenicMapper")
public interface ActivityScenicMapper extends BaseMapper<ActivityScenicEntity> {

}
