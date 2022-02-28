package com.aorise.mapper.message;

import com.aorise.model.message.MessageEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
* 留言板消息 DAO接口
* @author cat
* @version 1.0
*/
@Mapper
@Component(value = "messageMapper")
public interface MessageMapper extends BaseMapper<MessageEntity> {

}
