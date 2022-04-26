package com.aorise.service.scenic.impl;

import com.aorise.exceptions.ServiceException;
import com.aorise.mapper.checkpoint.CheckPointMapper;
import com.aorise.mapper.scenic.RouteCheckPointMapper;
import com.aorise.mapper.scenic.RouteMapper;
import com.aorise.mapper.scenic.ScenicAchievementMapper;
import com.aorise.mapper.scenic.ScenicMapper;
import com.aorise.model.checkpoint.CheckPointEntity;
import com.aorise.model.member.MemberEntity;
import com.aorise.model.message.MessageEntity;
import com.aorise.model.message.MessagePicEntity;
import com.aorise.model.scenic.RouteCheckPointEntity;
import com.aorise.model.scenic.RouteEntity;
import com.aorise.model.scenic.ScenicAchievementEntity;
import com.aorise.model.scenic.ScenicEntity;
import com.aorise.service.common.UploadService;
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
public class ScenicServiceImpl extends ServiceImpl<ScenicMapper, ScenicEntity> implements ScenicService {
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
     * 分页查询景点信息
     *
     * @return Page<ScenicEntity>
     * @params: page
     * @params: entity
     * @author cat
     * @date 2019-07-10
     * @modified By:
     */
    @Override
    public Page<ScenicEntity> getScenicByPage(Page<ScenicEntity> page, QueryWrapper<ScenicEntity> entity) {
        page = this.page(page, entity);
        List<ScenicEntity> scenicEntities = page.getRecords();
        if (scenicEntities.size() > 0) {
            for (ScenicEntity scenicEntity : scenicEntities) {
                //查询景点打卡人次
                QueryWrapper<ScenicAchievementEntity> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("scenic_id", scenicEntity.getId());
                int count = scenicAchievementMapper.selectCount(queryWrapper);
                scenicEntity.setFinishNum(count);
            }
        }

        return page;
    }

    /**
     * 根据ID查询景点信息
     *
     * @param id 景点ID
     * @return ScenicEntity
     * @author cat
     * @date Created in 2018/9/20 9:27
     * @modified By:
     */
    @Override
    public ScenicEntity getScenicById(Integer id) {
        QueryWrapper<ScenicEntity> entity = new QueryWrapper<>();
        entity.eq("id", id);
        entity.eq("is_delete", ConstDefine.IS_NOT_DELETE);
        ScenicEntity scenicEntity = this.getOne(entity);
        if (scenicEntity != null) {
            //查询路线
            QueryWrapper<RouteEntity> routeEntityQueryWrapper = new QueryWrapper<>();
            routeEntityQueryWrapper.eq("scenic_id", id);
            routeEntityQueryWrapper.eq("is_delete", ConstDefine.IS_NOT_DELETE);
            List<RouteEntity> routeEntities = routeMapper.selectList(routeEntityQueryWrapper);
            if (routeEntities.size() > 0) {
                scenicEntity.setRouteEntities(routeEntities);
            }

            //查询打卡点
            for (RouteEntity routeEntity : routeEntities) {
                //查询打卡点集合
                List<CheckPointEntity> checkPointEntities = new ArrayList<>();
                QueryWrapper<RouteCheckPointEntity> routeCheckPointEntityQueryWrapper = new QueryWrapper<>();
                routeCheckPointEntityQueryWrapper.eq("scenic_id", id);
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
        return scenicEntity;
    }

    /**
     * 新增景点
     *
     * @param scenicEntity 景点
     * @return int 影响行数
     * @author cat
     * @date Created in 2018/9/20 9:27
     * @modified By:
     */
    @Override
    public int addScenic(ScenicEntity scenicEntity) {

        boolean bol = this.save(scenicEntity);
        if (!bol) {
            throw new ServiceException("新增景点失败。");
        }

        return 1;
    }

    /**
     * 修改景点
     *
     * @param scenicEntity 景点
     * @param request      request
     * @return int 影响行数
     * @author cat
     * @date Created in 2018/9/20 9:27
     * @modified By:
     */
    @Override
    public int updateScenic(ScenicEntity scenicEntity, HttpServletRequest request) {
        boolean bol = this.updateById(scenicEntity);
        if (bol) {
            return 1;
        } else {
            return -1;
        }
    }

    /**
     * 删除景点
     *
     * @param id      景点ID
     * @param request request
     * @return int 影响行数
     * @author cat
     * @date Created in 2018/9/20 9:27
     * @modified By:
     */
    @Override
    public int deleteScenic(int id, HttpServletRequest request) {
        ScenicEntity scenicEntity = new ScenicEntity();
        scenicEntity.setId(id);
        scenicEntity.setIsDelete(ConstDefine.IS_DELETE);
        boolean bol = this.updateById(scenicEntity);
        if (bol) {
            //删除旧图片文件
            ScenicEntity oldScenic = this.getById(id);
            uploadService.deletefile(oldScenic.getBgi(), request);
            uploadService.deletefile(oldScenic.getPic(), request);

            //查询旧的路线
            QueryWrapper<RouteEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("scenic_id", id);
            queryWrapper.eq("is_delete", ConstDefine.IS_NOT_DELETE);
            List<RouteEntity> routeEntities = routeMapper.selectList(queryWrapper);
            if (routeEntities.size() > 0) {
                //删除旧的路线
                for (RouteEntity routeEntity : routeEntities) {
                    routeEntity.setIsDelete(ConstDefine.IS_DELETE);
                    int i = routeMapper.updateById(routeEntity);
                    if (i > 0) {
                        //删除图片文件
                        uploadService.deletefile(routeEntity.getPic(), request);
                    }else {
                        throw new ServiceException("删除路线失败。");
                    }
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
                }
            }
            return 1;
        } else {
            return -1;
        }
    }
}
