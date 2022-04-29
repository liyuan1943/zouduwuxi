package com.aorise.service.checkpoint.impl;

import com.aorise.mapper.checkpoint.CheckPointMapper;
import com.aorise.mapper.scenic.RouteCheckPointMapper;
import com.aorise.mapper.scenic.RouteMapper;
import com.aorise.model.checkpoint.CheckPointEntity;
import com.aorise.model.scenic.RouteCheckPointEntity;
import com.aorise.model.scenic.RouteEntity;
import com.aorise.service.checkpoint.CheckPointService;
import com.aorise.utils.define.ConstDefine;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Autowired
    RouteMapper routeMapper;

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
        //查询线路打卡点关系数据
        QueryWrapper<CheckPointEntity> checkPointEntityQueryWrapper = new QueryWrapper<>();
        if (scenicId != null) {
            checkPointEntityQueryWrapper.eq("scenic_id", scenicId);
        }
        checkPointEntityQueryWrapper.orderByAsc("scenic_id");
        List<CheckPointEntity> checkPointEntities =checkPointMapper.selectList(checkPointEntityQueryWrapper);
        for (CheckPointEntity checkPointEntity : checkPointEntities) {
            //查询线路
            QueryWrapper<RouteCheckPointEntity> checkPointRecordEntityQueryWrapper = new QueryWrapper<>();
            checkPointRecordEntityQueryWrapper.eq("scenic_id",checkPointEntity.getScenicId());
            checkPointRecordEntityQueryWrapper.eq("check_point_id",checkPointEntity.getId());
            checkPointRecordEntityQueryWrapper.orderByAsc("no");
            List<RouteCheckPointEntity> routeCheckPointEntities =routeCheckPointMapper.selectList(checkPointRecordEntityQueryWrapper);
            if(routeCheckPointEntities.size()>0){
                StringBuilder route= new StringBuilder();
                for(RouteCheckPointEntity routeCheckPointEntity :routeCheckPointEntities){
                    RouteEntity routeEntity = routeMapper.selectById(routeCheckPointEntity.getRouteId());
                    if(routeEntity!=null) {
                        if (routeCheckPointEntity.getIsDestination().equals(ConstDefine.IS_YES)) {
                            route.append(",").append(routeEntity.getName()).append("(终点)");
                        }else {
                            route.append(",").append(routeEntity.getName()).append("(打卡点").append(routeCheckPointEntity.getNo()).append(")");
                        }
                    }
                }
                if(StringUtils.isNotBlank(route.toString())){
                    route = new StringBuilder(route.substring(1));
                    checkPointEntity.setRoute(route.toString());
                }
            }
        }

        return checkPointEntities;
    }

}
