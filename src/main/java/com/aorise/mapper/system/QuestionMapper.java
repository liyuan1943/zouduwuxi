package com.aorise.mapper.system;

import com.aorise.model.system.ConfigEntity;
import com.aorise.model.system.QuestionEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
* 常见问题 DAO接口
* @author cat
* @version 1.0
*/
@Mapper
@Component(value = "questionMapper")
public interface QuestionMapper extends BaseMapper<QuestionEntity> {

}
