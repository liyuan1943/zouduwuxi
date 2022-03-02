package com.aorise.mapper.medal;

import com.aorise.model.banner.BannerEntity;
import com.aorise.model.medal.MedalEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
* 勋章 DAO接口
* @author cat
* @version 1.0
*/
@Mapper
@Component(value = "medalMapper")
public interface MedalMapper extends BaseMapper<MedalEntity> {

}
