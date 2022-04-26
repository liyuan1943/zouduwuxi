package com.aorise.service.checkpoint.impl;

import com.aorise.mapper.checkpoint.CheckPointMapper;
import com.aorise.mapper.scenic.RouteCheckPointMapper;
import com.aorise.model.checkpoint.CheckPointEntity;
import com.aorise.model.scenic.RouteCheckPointEntity;
import com.aorise.model.scenic.RouteEntity;
import com.aorise.service.checkpoint.CheckPointService;
import com.aorise.utils.define.ConstDefine;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * 打卡点 ServiceImpl层
 *
 * @author cat
 * @version 1.0
 */
@Service
@Transactional(rollbackFor = {Exception.class})
public class CheckPointServiceImpl extends ServiceImpl<CheckPointMapper, CheckPointEntity> implements CheckPointService {

    @Autowired
    RouteCheckPointMapper routeCheckPointMapper;
    @Autowired
    CheckPointMapper checkPointMapper;

    /**
     * 查询所有打卡点信息
     *
     * @param scenicId 景点ID
     * @return List<CheckPointEntity>
     * @author cat
     * @date 2019-07-10
     */
    @Override
    public List<CheckPointEntity> getAllCheckPoint(Integer scenicId) {
        List<CheckPointEntity> checkPointEntities = new ArrayList<>();
        //查询线路打卡点关系数据
        QueryWrapper<RouteCheckPointEntity> routeCheckPointEntityQueryWrapper = new QueryWrapper<>();
        if (scenicId != null) {
            routeCheckPointEntityQueryWrapper.eq("scenic_id", scenicId);
        }
        routeCheckPointEntityQueryWrapper.orderByAsc("scenic_id");
        routeCheckPointEntityQueryWrapper.orderByAsc("route_id");
        routeCheckPointEntityQueryWrapper.orderByAsc("no");
        List<RouteCheckPointEntity> routeCheckPointEntities = routeCheckPointMapper.selectList(routeCheckPointEntityQueryWrapper);
        for (RouteCheckPointEntity routeCheckPointEntity : routeCheckPointEntities) {
            CheckPointEntity checkPointEntity = checkPointMapper.selectById(routeCheckPointEntity.getCheckPointId());
            checkPointEntities.add(checkPointEntity);
        }

        return checkPointEntities;
    }

}
