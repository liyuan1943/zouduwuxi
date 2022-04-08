package com.aorise.mapper.scenic;

import com.aorise.model.scenic.ScenicAchievementEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
* 景点成就表 DAO接口
* @author cat
* @version 1.0
*/
@Mapper
@Component(value = "scenicAchievementMapper")
public interface ScenicAchievementMapper extends BaseMapper<ScenicAchievementEntity> {

}
