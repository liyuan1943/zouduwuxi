package com.aorise.mapper.scenic;

import com.aorise.model.scenic.RouteCheckPointEntity;
import com.aorise.model.scenic.RouteEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
* 路线打卡点关联表 DAO接口
* @author cat
* @version 1.0
*/
@Mapper
@Component(value = "routeCheckPointMapper")
public interface RouteCheckPointMapper extends BaseMapper<RouteCheckPointEntity> {

}
