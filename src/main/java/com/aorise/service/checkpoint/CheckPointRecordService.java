package com.aorise.service.checkpoint;

import com.aorise.model.checkpoint.CheckPointEntity;
import com.aorise.model.checkpoint.CheckPointRecordEntity;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;


/**
* 打卡记录 Service层
* @author cat
* @version 1.0
*/
public interface CheckPointRecordService extends IService<CheckPointRecordEntity> {
    /**
     * 根据条件分页查询打卡记录
     *
     * @param map  查询条件
     * @param page 分页
     * @return Page 打卡记录信息集合
     * @author yulu
     * @date 2020-08-14
     */
    Page<CheckPointRecordEntity> getCheckPointRecordByPage(Map<String, Object> map, Page<CheckPointRecordEntity> page);
}
