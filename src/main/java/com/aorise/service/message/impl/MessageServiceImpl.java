package com.aorise.service.message.impl;

import com.aorise.exceptions.ServiceException;
import com.aorise.mapper.message.MessageMapper;
import com.aorise.mapper.message.MessagePicMapper;
import com.aorise.mapper.token.TokenMapper;
import com.aorise.model.member.MemberEntity;
import com.aorise.model.message.MessageEntity;
import com.aorise.model.message.MessagePicEntity;
import com.aorise.model.token.TokenEntity;
import com.aorise.service.common.UploadService;
import com.aorise.service.member.MemberService;
import com.aorise.service.message.MessageService;
import com.aorise.utils.define.ConstDefine;
import com.aorise.utils.setting.FileuploadSetting;
import com.aorise.utils.wechat.WeiXinSecurityUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private TokenMapper tokenMapper;
    @Autowired
    private FileuploadSetting fileuploadSetting;

    /**
     * 分页查询留言信息
     *
     * @param : page 分页
     * @param : entity 条件
     * @return Page<Message>
     * @author cat
     * @date 2019-07-10
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
                    StringBuilder pics = new StringBuilder();
                    for (MessagePicEntity messagePicEntity : messagePicEntities) {
                        pics.append(messagePicEntity.getPic()).append(",");
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
     */
    @Override
    public int addMessage(MessageEntity messageEntity, HttpServletRequest request) {
        //从数据库获取微信token
        Map<String, Object> map = new HashMap<>();
        map.put("id", ConstDefine.TOKEN_ID_WECHAT_PRO);
        TokenEntity tokenEntity = tokenMapper.getTokenById(map);

        //验证文字合法性
        boolean bool = WeiXinSecurityUtil.securityMsgSecCheck(tokenEntity.getToken(), messageEntity.getContent());
        if (!bool) {
            //删除所有图片
            if (StringUtils.isNotBlank(messageEntity.getMessagePics())) {
                String[] pics = messageEntity.getMessagePics().split(",");
                for (String messagePicDel : pics) {
                    //删除图片文件
                    int indexDel = messagePicDel.lastIndexOf("/");
                    String fileNameDel = messagePicDel.substring(indexDel + 1);
                    uploadService.deleteImg(fileNameDel, request);
                }
            }
            throw new ServiceException("文字含有违法违规内容");
        }

        //验证图片合法性
        if (StringUtils.isNotBlank(messageEntity.getMessagePics())) {
            String[] pics = messageEntity.getMessagePics().split(",");
            int i = 1;
            for (String messagePic : pics) {
                int index = messagePic.lastIndexOf("/");
                String fileName = messagePic.substring(index + 1);
                String filePath = request.getSession().getServletContext().getRealPath(fileuploadSetting.getSavepath());
                File file = new File(filePath + fileName);
                boolean bool2 = WeiXinSecurityUtil.securityImgSecCheck(tokenEntity.getToken(), file);
                if (!bool2) {
                    //删除所有图片
                    for (String messagePicDel : pics) {
                        //删除图片文件
                        int indexDel = messagePicDel.lastIndexOf("/");
                        String fileNameDel = messagePicDel.substring(indexDel + 1);
                        uploadService.deleteImg(fileNameDel, request);
                    }
                    throw new ServiceException("第" + i + "张图片含有违法违规内容");
                }
                i++;
            }
        }

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
