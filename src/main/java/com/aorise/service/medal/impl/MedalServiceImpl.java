package com.aorise.service.medal.impl;

import com.aorise.mapper.banner.BannerMapper;
import com.aorise.mapper.medal.MedalMapper;
import com.aorise.model.banner.BannerEntity;
import com.aorise.model.medal.MedalEntity;
import com.aorise.service.medal.MedalService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* 勋章 ServiceImpl层
* @author cat
* @version 1.0
*/
@Service
@Transactional(rollbackFor = {Exception.class})
public class MedalServiceImpl extends ServiceImpl<MedalMapper, MedalEntity> implements MedalService {

}
