package com.aorise.service.checkpoint;

import com.aorise.model.checkpoint.CheckPointEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
* 打卡点 Service层
* @author cat
* @version 1.0
*/
public interface CheckPointService extends IService<CheckPointEntity> {

    /**
     * 查询所有打卡点信息
     *
     * @param scenicId 景点ID
     * @return List<CheckPointEntity>
     * @author cat
     * @date 2019-07-10
     */
    List<CheckPointEntity> getAllCheckPoint(Integer scenicId);
}
