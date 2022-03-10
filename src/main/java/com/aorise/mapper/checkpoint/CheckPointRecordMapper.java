package com.aorise.mapper.checkpoint;

import com.aorise.model.checkpoint.CheckPointRecordEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
* 打卡记录 DAO接口
* @author cat
* @version 1.0
*/
@Mapper
@Component(value = "checkPointRecordMapper")
public interface CheckPointRecordMapper extends BaseMapper<CheckPointRecordEntity> {

}
