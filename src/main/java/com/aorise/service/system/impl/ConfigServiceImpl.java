package com.aorise.service.system.impl;

import com.aorise.mapper.system.ConfigMapper;
import com.aorise.model.system.ConfigEntity;
import com.aorise.service.system.ConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* 系统信息配置 ServiceImpl层
* @author cat
* @version 1.0
*/
@Service
@Transactional(rollbackFor = {Exception.class})
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, ConfigEntity> implements ConfigService {

}
