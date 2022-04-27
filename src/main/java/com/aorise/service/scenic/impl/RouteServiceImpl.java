package com.aorise.service.scenic.impl;

import com.aorise.exceptions.ServiceException;
import com.aorise.mapper.checkpoint.CheckPointMapper;
import com.aorise.mapper.scenic.RouteCheckPointMapper;
import com.aorise.mapper.scenic.RouteMapper;
import com.aorise.mapper.scenic.ScenicAchievementMapper;
import com.aorise.mapper.scenic.ScenicMapper;
import com.aorise.model.checkpoint.CheckPointEntity;
import com.aorise.model.scenic.RouteCheckPointEntity;
import com.aorise.model.scenic.RouteEntity;
import com.aorise.model.scenic.ScenicAchievementEntity;
import com.aorise.model.scenic.ScenicEntity;
import com.aorise.service.common.UploadService;
import com.aorise.service.scenic.RouteService;
import com.aorise.service.scenic.ScenicService;
import com.aorise.utils.define.ConstDefine;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 景点路线 ServiceImpl层
 *
 * @author cat
 * @version 1.0
 */
@Service
@Transactional(rollbackFor = {Exception.class})
public class RouteServiceImpl extends ServiceImpl<RouteMapper, RouteEntity> implements RouteService {
    @Autowired
    CheckPointMapper checkPointMapper;
    @Autowired
    ScenicAchievementMapper scenicAchievementMapper;
    @Autowired
    UploadService uploadService;
    @Autowired
    RouteMapper routeMapper;
    @Autowired
    RouteCheckPointMapper routeCheckPointMapper;

    /**
     * 查询路线列表
     *
     * @param scenicId 景点ID
     * @return List<RouteEntity>
     * @author cat
     * @date 2019-07-10
     */
    @Override
    public List<RouteEntity> getRouteList(Integer scenicId) {
        //装载查询条件
        QueryWrapper<RouteEntity> entity = new QueryWrapper<>();
        if (scenicId != null) {
            entity.like("scenic_id", scenicId);
        }
        entity.eq("is_delete", ConstDefine.IS_NOT_DELETE);
        entity.orderByAsc("id");
        List<RouteEntity> routeEntities = this.list(entity);
        if (routeEntities.size() > 0) {
            for (RouteEntity routeEntity : routeEntities) {
                //查询打卡点集合
                List<CheckPointEntity> checkPointEntities = new ArrayList<>();
                QueryWrapper<RouteCheckPointEntity> routeCheckPointEntityQueryWrapper = new QueryWrapper<>();
                routeCheckPointEntityQueryWrapper.eq("scenic_id", scenicId);
                routeCheckPointEntityQueryWrapper.eq("route_id", routeEntity.getId());
                routeCheckPointEntityQueryWrapper.orderByAsc("no");
                List<RouteCheckPointEntity> routeCheckPointEntities = routeCheckPointMapper.selectList(routeCheckPointEntityQueryWrapper);
                for (RouteCheckPointEntity routeCheckPointEntity : routeCheckPointEntities) {
                    CheckPointEntity checkPointEntity = checkPointMapper.selectById(routeCheckPointEntity.getCheckPointId());
                    checkPointEntities.add(checkPointEntity);
                }
                routeEntity.setCheckPointEntities(checkPointEntities);
            }
        }
        return routeEntities;
    }

    /**
     * 新增路线
     *
     * @param routeEntity 路线
     * @return int 影响行数
     * @author cat
     * @date Created in 2018/9/20 9:27
     * @modified By:
     */
    @Override
    public int addRoute(RouteEntity routeEntity) {
        boolean bol = this.save(routeEntity);
        if (!bol) {
            throw new ServiceException("新增景点路线失败。");
        }

        //打卡点序号
        int i = 1;
        //新增打卡点
        List<CheckPointEntity> checkPointEntities = routeEntity.getCheckPointEntities();
        for (CheckPointEntity checkPointEntity : checkPointEntities) {
            if (checkPointEntity.getId() == null || checkPointEntity.getId().equals(0)) {
                //新的打卡点，增加打卡点信息表数据
                int iRete = checkPointMapper.insert(checkPointEntity);
                if (iRete <= 0) {
                    throw new ServiceException("新增新的打卡点失败。");
                }
            } else {
                //已存在的打卡点，修改打卡点信息表数据
                int iRete = checkPointMapper.updateById(checkPointEntity);
                if (iRete <= 0) {
                    throw new ServiceException("修改已存在的打卡点失败。");
                }
            }
            //新增路线打卡点关系
            RouteCheckPointEntity routeCheckPointEntity = new RouteCheckPointEntity();
            routeCheckPointEntity.setScenicId(routeEntity.getScenicId());
            routeCheckPointEntity.setRouteId(routeEntity.getId());
            routeCheckPointEntity.setCheckPointId(checkPointEntity.getId());
            routeCheckPointEntity.setNo(i);
            if (i == checkPointEntities.size()) {
                routeCheckPointEntity.setIsDestination(ConstDefine.IS_YES);
            } else {
                routeCheckPointEntity.setIsDestination(ConstDefine.IS_NO);
            }
            //增加路线打卡点关系表数据
            int iRet = routeCheckPointMapper.insert(routeCheckPointEntity);
            if (iRet <= 0) {
                throw new ServiceException("新增路线打卡点关系失败。");
            }
            i++;
        }
        return 1;
    }

    /**
     * 修改路线
     *
     * @param routeEntity 路线
     * @return int 影响行数
     * @author cat
     * @date Created in 2018/9/20 9:27
     */
    @Override
    public int updateRoute(RouteEntity routeEntity) {
        boolean bol = this.updateById(routeEntity);
        if (bol) {
            //查询该路线旧的打卡点关系
            QueryWrapper<RouteCheckPointEntity> routeCheckPointEntityQueryWrapper = new QueryWrapper<>();
            routeCheckPointEntityQueryWrapper.eq("route_id", routeEntity.getId());
            List<RouteCheckPointEntity> routeCheckPointEntities = routeCheckPointMapper.selectList(routeCheckPointEntityQueryWrapper);
            for (RouteCheckPointEntity routeCheckPointEntity : routeCheckPointEntities) {
                //删除旧的关系
                int iRet = routeCheckPointMapper.deleteById(routeCheckPointEntity.getId());
                if (iRet <= 0) {
                    throw new ServiceException("删除路线打卡点关系失败。");
                }
            }

            //打卡点序号
            int i = 1;
            //新增打卡点
            List<CheckPointEntity> checkPointEntities = routeEntity.getCheckPointEntities();
            for (CheckPointEntity checkPointEntity : checkPointEntities) {
                if (checkPointEntity.getId() == null || checkPointEntity.getId().equals(0)) {
                    //新的打卡点，增加打卡点信息表数据
                    int iRete = checkPointMapper.insert(checkPointEntity);
                    if (iRete <= 0) {
                        throw new ServiceException("新增新的打卡点失败。");
                    }
                } else {
                    //已存在的打卡点，修改打卡点信息表数据
                    int iRete = checkPointMapper.updateById(checkPointEntity);
                    if (iRete <= 0) {
                        throw new ServiceException("修改已存在的打卡点失败。");
                    }
                }
                //新增路线打卡点关系
                RouteCheckPointEntity routeCheckPointEntity = new RouteCheckPointEntity();
                routeCheckPointEntity.setScenicId(routeEntity.getScenicId());
                routeCheckPointEntity.setRouteId(routeEntity.getId());
                routeCheckPointEntity.setCheckPointId(checkPointEntity.getId());
                routeCheckPointEntity.setNo(i);
                if (i == checkPointEntities.size()) {
                    routeCheckPointEntity.setIsDestination(ConstDefine.IS_YES);
                } else {
                    routeCheckPointEntity.setIsDestination(ConstDefine.IS_NO);
                }
                //增加路线打卡点关系表数据
                int iRet = routeCheckPointMapper.insert(routeCheckPointEntity);
                if (iRet <= 0) {
                    throw new ServiceException("新增路线打卡点关系失败。");
                }
                i++;
            }

            return 1;
        } else {
            return -1;
        }
    }

    /**
     * 删除路线
     *
     * @param id      路线ID
     * @param request request
     * @return int 影响行数
     * @author cat
     * @date Created in 2018/9/20 9:27
     */
    @Override
    public int deleteRoute(int id, HttpServletRequest request) {
        RouteEntity routeEntity = new RouteEntity();
        routeEntity.setId(id);
        routeEntity.setIsDelete(ConstDefine.IS_DELETE);
        boolean bol = this.updateById(routeEntity);
        if (bol) {
            //删除旧图片文件
            RouteEntity oldScenic = this.getById(id);
            uploadService.deletefile(oldScenic.getPic(), request);

            //查询该路线的打卡点关系
            QueryWrapper<RouteCheckPointEntity> routeCheckPointEntityQueryWrapper = new QueryWrapper<>();
            routeCheckPointEntityQueryWrapper.eq("route_id", routeEntity.getId());
            List<RouteCheckPointEntity> routeCheckPointEntities = routeCheckPointMapper.selectList(routeCheckPointEntityQueryWrapper);
            for (RouteCheckPointEntity routeCheckPointEntity : routeCheckPointEntities) {
                //删除关系
                int iRet = routeCheckPointMapper.deleteById(routeCheckPointEntity.getId());
                if (iRet <= 0) {
                    throw new ServiceException("删除路线打卡点关系失败。");
                }
            }
            return 1;
        } else {
            return -1;
        }
    }
}
