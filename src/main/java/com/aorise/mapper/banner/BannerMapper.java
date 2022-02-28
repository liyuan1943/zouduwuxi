package com.aorise.mapper.banner;

import com.aorise.model.banner.BannerEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
* 轮播图 DAO接口
* @author cat
* @version 1.0
*/
@Mapper
@Component(value = "bannerMapper")
public interface BannerMapper extends BaseMapper<BannerEntity> {

}
