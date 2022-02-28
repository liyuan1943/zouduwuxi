package com.aorise.mapper.message;

import com.aorise.model.message.MessagePicEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
* 留言图片 DAO接口
* @author cat
* @version 1.0
*/
@Mapper
@Component(value = "messagePicMapper")
public interface MessagePicMapper extends BaseMapper<MessagePicEntity> {

}
