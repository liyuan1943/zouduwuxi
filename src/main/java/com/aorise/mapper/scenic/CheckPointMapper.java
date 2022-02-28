package com.aorise.mapper.scenic;

import com.aorise.model.scenic.CheckPointEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
* 景点打卡点 DAO接口
* @author cat
* @version 1.0
*/
@Mapper
@Component(value = "checkPointMapper")
public interface CheckPointMapper extends BaseMapper<CheckPointEntity> {

}
