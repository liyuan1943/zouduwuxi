package com.aorise.service.wechat;

import com.aorise.model.wechat.TemplateMessageModel;


/**
 * @Author:cat
 * @Description 模板消息服务类
 * @Date: 2018-04-12  10:01
 * @Modified By:
 */
public interface TemplateService {

    /**
     * 发送模版消息
     * @param templateMessageModel 模板消息Model
     * @return 0表示成功，其他值表示失败
     */
    int SendTemplateMessage(TemplateMessageModel templateMessageModel);
}
