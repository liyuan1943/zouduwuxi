package com.aorise.service.banner.impl;

import com.aorise.mapper.banner.BannerMapper;
import com.aorise.model.banner.BannerEntity;
import com.aorise.service.banner.BannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* 轮播图 ServiceImpl层
* @author cat
* @version 1.0
*/
@Service
@Transactional(rollbackFor = {Exception.class})
public class BannerServiceImpl extends ServiceImpl<BannerMapper, BannerEntity> implements BannerService {

}
