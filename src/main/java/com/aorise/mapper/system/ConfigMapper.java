package com.aorise.mapper.system;

import com.aorise.model.system.ConfigEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
* 系统信息配置 DAO接口
* @author cat
* @version 1.0
*/
@Mapper
@Component(value = "configMapper")
public interface ConfigMapper extends BaseMapper<ConfigEntity> {

}
