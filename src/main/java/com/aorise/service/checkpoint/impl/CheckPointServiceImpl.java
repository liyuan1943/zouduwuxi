package com.aorise.service.checkpoint.impl;

import com.aorise.mapper.checkpoint.CheckPointMapper;
import com.aorise.model.checkpoint.CheckPointEntity;
import com.aorise.service.checkpoint.CheckPointService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 打卡点 ServiceImpl层
 *
 * @author cat
 * @version 1.0
 */
@Service
@Transactional(rollbackFor = {Exception.class})
public class CheckPointServiceImpl extends ServiceImpl<CheckPointMapper, CheckPointEntity> implements CheckPointService {


}
