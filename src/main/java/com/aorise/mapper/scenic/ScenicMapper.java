package com.aorise.mapper.scenic;

import com.aorise.model.scenic.ScenicEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
* 景点 DAO接口
* @author cat
* @version 1.0
*/
@Mapper
@Component(value = "scenicMapper")
public interface ScenicMapper extends BaseMapper<ScenicEntity> {

}
