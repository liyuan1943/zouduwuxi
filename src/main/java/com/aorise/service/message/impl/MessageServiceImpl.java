package com.aorise.service.message.impl;

import com.aorise.mapper.message.MessageMapper;
import com.aorise.mapper.message.MessagePicMapper;
import com.aorise.model.member.MemberEntity;
import com.aorise.model.message.MessageEntity;
import com.aorise.model.message.MessagePicEntity;
import com.aorise.service.common.UploadService;
import com.aorise.service.member.MemberService;
import com.aorise.service.message.MessageService;
import com.aorise.utils.define.ConstDefine;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 留言 ServiceImpl层
 *
 * @author cat
 * @version 1.0
 */
@Service
@Transactional(rollbackFor = {Exception.class})
public class MessageServiceImpl extends ServiceImpl<MessageMapper, MessageEntity> implements MessageService {

    @Autowired
    MessagePicMapper messagePicMapper;
    @Autowired
    MemberService memberService;
    @Autowired
    UploadService uploadService;


    /**
     * 分页查询留言信息
     *
     * @return Page<Message>
     * @params: page
     * @params: entity
     * @author cat
     * @date 2019-07-10
     * @modified By:
     */
    @Override
    public Page<MessageEntity> getMessageByPage(Page<MessageEntity> page, QueryWrapper<MessageEntity> entity) {
        page = this.page(page, entity);
        List<MessageEntity> messageEntities = page.getRecords();
        if (messageEntities.size() > 0) {
            for (MessageEntity messageEntity : messageEntities) {
                //查询会员信息
                MemberEntity memberEntity = memberService.getById(messageEntity.getMemberId());
                if (memberEntity != null) {
                    messageEntity.setNickname(memberEntity.getNickname());
                    messageEntity.setHeadPic(memberEntity.getHeadPic());
                }
                //查询留言图片
                QueryWrapper<MessagePicEntity> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("message_id", messageEntity.getId());
                queryWrapper.orderByAsc("sort");
                List<MessagePicEntity> messagePicEntities = messagePicMapper.selectList(queryWrapper);
                if (messagePicEntities.size() > 0) {
                    String pics = "";
                    for (MessagePicEntity messagePicEntity : messagePicEntities) {
                        pics += messagePicEntity.getPic() + ",";
                    }
                    messageEntity.setMessagePics(pics.substring(0, pics.length() - 1));
                }
            }
        }

        return page;
    }

    /**
     * 新增留言
     *
     * @param messageEntity 留言
     * @return int 影响行数
     * @author cat
     * @date Created in 2018/9/20 9:27
     * @modified By:
     */
    @Override
    public int addMessage(MessageEntity messageEntity) {
        boolean bol = this.save(messageEntity);
        if (bol) {
            //新增留言图片
            if (StringUtils.isNotBlank(messageEntity.getMessagePics())) {
                String[] pics = messageEntity.getMessagePics().split(",");
                int i = 1;
                for (String messagePic : pics) {
                    MessagePicEntity messagePicEntity = new MessagePicEntity();
                    messagePicEntity.setMessageId(messageEntity.getId());
                    messagePicEntity.setPic(messagePic);
                    messagePicEntity.setSort(i);
                    messagePicMapper.insert(messagePicEntity);
                    i++;
                }
            }
            return 1;
        } else {
            return -1;
        }
    }

    /**
     * 删除留言
     *
     * @param id 留言ID
     * @return int 影响行数
     * @author cat
     * @date Created in 2018/9/20 9:27
     * @modified By:
     */
    @Override
    public int deleteMessage(Integer id, HttpServletRequest request) {
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setId(id);
        messageEntity.setIsDelete(ConstDefine.IS_DELETE);
        boolean bol = this.updateById(messageEntity);
        if (bol) {
            //查询图片文件
            QueryWrapper<MessagePicEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("message_id", id);
            List<MessagePicEntity> messagePicEntities = messagePicMapper.selectList(queryWrapper);
            if (messagePicEntities.size() > 0) {
                for (MessagePicEntity messagePicEntity : messagePicEntities) {
                    //删除图片文件
                    uploadService.deletefile(messagePicEntity.getPic(), request);
                }
            }
            return 1;
        } else {
            return -1;
        }
    }
}
