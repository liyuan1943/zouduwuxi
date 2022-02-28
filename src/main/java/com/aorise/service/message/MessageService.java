package com.aorise.service.message;

import com.aorise.model.message.MessageEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
* 留言 Service层
* @author cat
* @version 1.0
*/
public interface MessageService extends IService<MessageEntity> {

    /**
     * 分页查询留言信息
     * @params: page
     * @params: entity
     * @return Page<Message>
     * @author cat
     * @date 2019-07-10
     * @modified By:
     */
    Page<MessageEntity> getMessageByPage(Page<MessageEntity> page, QueryWrapper<MessageEntity> entity);

    /**
     * 新增留言
     * @param messageEntity 留言
     * @return int 影响行数
     * @author cat
     * @date  Created in 2018/9/20 9:27
     * @modified By:
     */
    int addMessage(MessageEntity messageEntity);

    /**
     * 删除留言
     * @param id 留言ID
     * @return int 影响行数
     * @author cat
     * @date  Created in 2018/9/20 9:27
     * @modified By:
     */
    int deleteMessage(Integer id, HttpServletRequest request);
}
