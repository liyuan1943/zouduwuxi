package com.aorise.service.checkpoint.impl;

import com.aorise.mapper.checkpoint.CheckPointMapper;
import com.aorise.mapper.checkpoint.CheckPointRecordMapper;
import com.aorise.model.checkpoint.CheckPointEntity;
import com.aorise.model.checkpoint.CheckPointRecordEntity;
import com.aorise.service.checkpoint.CheckPointRecordService;
import com.aorise.service.checkpoint.CheckPointService;
import com.aorise.utils.define.ConstDefine;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


/**
 * 打卡记录 ServiceImpl层
 *
 * @author cat
 * @version 1.0
 */
@Service
@Transactional(rollbackFor = {Exception.class})
public class CheckPointRecordServiceImpl extends ServiceImpl<CheckPointRecordMapper, CheckPointRecordEntity> implements CheckPointRecordService {

    @Autowired
    CheckPointService checkPointService;

    /**
     * 根据条件分页查询打卡记录
     *
     * @param map  查询条件
     * @param page 分页
     * @return Page 打卡记录信息集合
     * @author yulu
     * @date 2020-08-14
     */
    @Override
    public Page<CheckPointRecordEntity> getCheckPointRecordByPage(Map<String, Object> map, Page<CheckPointRecordEntity> page) {
        Integer memberId = map.get("memberId") == null ? null : Integer.parseInt(map.get("memberId").toString());
        Integer scenicId = map.get("scenicId") == null ? null : Integer.parseInt(map.get("scenicId").toString());

        //装载查询条件
        QueryWrapper<CheckPointRecordEntity> queryWrapper = new QueryWrapper<>();
        if (memberId!=null ) {
            queryWrapper.eq("member_id", memberId);
        }
        if (scenicId!=null ) {
            queryWrapper.eq("scenic_id", scenicId);
        }

        queryWrapper.eq("is_delete", ConstDefine.IS_NOT_DELETE);
        queryWrapper.orderByDesc("create_date");
        page = this.page(page, queryWrapper);

        //查询打卡点名称
        List<CheckPointRecordEntity> entities = page.getRecords();
        if (entities != null) {
            for (CheckPointRecordEntity checkPointRecordEntity : entities) {
                CheckPointEntity checkPointEntity = checkPointService.getById(checkPointRecordEntity.getCheckPointId());
                if(checkPointEntity!= null){
                    checkPointRecordEntity.setCheckPointName(checkPointEntity.getName());
                }
            }
        }
        return page;
    }

}
