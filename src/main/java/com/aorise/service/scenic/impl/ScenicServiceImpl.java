package com.aorise.service.scenic.impl;

import com.aorise.mapper.scenic.CheckPointMapper;
import com.aorise.mapper.scenic.ScenicMapper;
import com.aorise.model.scenic.CheckPointEntity;
import com.aorise.model.scenic.ScenicEntity;
import com.aorise.service.common.UploadService;
import com.aorise.service.scenic.ScenicService;
import com.aorise.utils.define.ConstDefine;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 景点 ServiceImpl层
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
    UploadService uploadService;

    /**
     * 根据ID查询景点信息
     * @param id 景点ID
     * @return ScenicEntity
     * @author cat
     * @date  Created in 2018/9/20 9:27
     * @modified By:
     */
    @Override
    public ScenicEntity getScenicById(Integer id) {
        QueryWrapper<ScenicEntity> entity = new QueryWrapper<>();
        entity.eq("id", id);
        entity.eq("is_delete", ConstDefine.IS_NOT_DELETE);
        ScenicEntity scenicEntity = this.getOne(entity);
        if(scenicEntity != null){
            QueryWrapper<CheckPointEntity> checkPointEntityQueryWrapper = new QueryWrapper<>();
            checkPointEntityQueryWrapper.eq("scenic_id", id);
            checkPointEntityQueryWrapper.eq("is_delete", ConstDefine.IS_NOT_DELETE);
            List<CheckPointEntity> checkPointEntities = checkPointMapper.selectList(checkPointEntityQueryWrapper);
            if(checkPointEntities.size()>0){
                scenicEntity.setCheckPointEntities(checkPointEntities);
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
        if (bol) {
            //新增打卡点
            for (CheckPointEntity checkPointEntity : scenicEntity.getCheckPointEntities()) {
                checkPointEntity.setScenicId(scenicEntity.getId());
                checkPointMapper.insert(checkPointEntity);
            }
            return 1;
        } else {
            return -1;
        }
    }

    /**
     * 修改景点
     *
     * @param scenicEntity 景点
     * @param request request
     * @return int 影响行数
     * @author cat
     * @date Created in 2018/9/20 9:27
     * @modified By:
     */
    @Override
    public int updateScenic(ScenicEntity scenicEntity, HttpServletRequest request) {
        ScenicEntity oldScenic = this.getById(scenicEntity.getId());
        boolean bol = this.updateById(scenicEntity);
        if (bol) {
            //删除图片文件
            if (!oldScenic.getBgi().equals(scenicEntity.getBgi())) {
                uploadService.deletefile(oldScenic.getBgi(), request);
            }
            if (!oldScenic.getPic().equals(scenicEntity.getPic())) {
                uploadService.deletefile(oldScenic.getPic(), request);
            }

            //查询旧打卡点
            QueryWrapper<CheckPointEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("scenic_id",scenicEntity.getId());
            List<CheckPointEntity> checkPointEntities =checkPointMapper.selectList(queryWrapper);
            if(checkPointEntities.size()>0){
                //删除旧的打卡点
                for(CheckPointEntity checkPointEntity : checkPointEntities){
                    checkPointEntity.setIsDelete(ConstDefine.IS_DELETE);
                    checkPointMapper.updateById(checkPointEntity);
                }
            }

            //新增打卡点
            for (CheckPointEntity checkPointEntity : scenicEntity.getCheckPointEntities()) {
                checkPointEntity.setScenicId(scenicEntity.getId());
                checkPointMapper.insert(checkPointEntity);
            }
            return 1;
        } else {
            return -1;
        }
    }

    /**
     * 删除景点
     *
     * @param id 景点ID
     * @param request request
     * @return int 影响行数
     * @author cat
     * @date Created in 2018/9/20 9:27
     * @modified By:
     */
    @Override
    public int deleteScenic(int id, HttpServletRequest request) {
        ScenicEntity scenicEntity =new ScenicEntity();
        scenicEntity.setId(id);
        scenicEntity.setIsDelete(ConstDefine.IS_DELETE);
        boolean bol = this.updateById(scenicEntity);
        if (bol) {
            //删除旧图片文件
            ScenicEntity oldScenic = this.getById(id);
            uploadService.deletefile(oldScenic.getBgi(), request);
            uploadService.deletefile(oldScenic.getPic(), request);

            //查询旧打卡点
            QueryWrapper<CheckPointEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("scenic_id",id);
            queryWrapper.eq("is_delete",ConstDefine.IS_NOT_DELETE);
            List<CheckPointEntity> checkPointEntities =checkPointMapper.selectList(queryWrapper);
            if(checkPointEntities.size()>0){
                //删除旧的打卡点
                for(CheckPointEntity checkPointEntity : checkPointEntities){
                    checkPointEntity.setIsDelete(ConstDefine.IS_DELETE);
                    checkPointMapper.updateById(checkPointEntity);
                }
            }
            return 1;
        } else {
            return -1;
        }
    }
}
