package com.aorise.service.wechat.impl;

import com.aorise.mapper.token.TokenMapper;
import com.aorise.model.token.TokenEntity;
import com.aorise.model.wechat.TemplateMessageModel;
import com.aorise.service.wechat.TemplateService;
import com.aorise.utils.define.ConstDefine;
import com.aorise.utils.wechat.WeixinUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
/**
 * @Author:cat
 * @Description 模板消息接口实现
 * @Date: 2018-04-12  10:01
 * @Modified By:
 */
public class TemplateServiceImpl implements TemplateService {

    private static Logger log = LoggerFactory.getLogger(TemplateServiceImpl.class);

    @Autowired
    private TokenMapper tokenMapper;

    // 发送模版消息（POST）
    public static String template_url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";

    /**
     * 发送模版消息
     *
     * @param templateMessageModel 模板消息Model
     * @return 0表示成功，其他值表示失败
     */
    @Override
    @Async
    public int SendTemplateMessage(TemplateMessageModel templateMessageModel) {
        int result = 0;
        /**从数据库获取微信token*/
        Map<String, Object> mapw = new HashMap<>();
        mapw.put("id", ConstDefine.TOKEN_ID_WECHAT);
        TokenEntity tokenEntity = tokenMapper.getTokenById(mapw);

        String jsonTemplate="";
        if (tokenEntity != null) {
            templateMessageModel.setAccessToken(tokenEntity.getToken());

            //拼装json
            StringBuilder sb = new StringBuilder("{\"touser\":\"" + templateMessageModel.getOpenId() + "\",");
            sb.append("\"template_id\":\"" + templateMessageModel.getTemplateId() + "\",");
            if (templateMessageModel.getUrl() != null && templateMessageModel.getUrl() != "") {
                sb.append("\"url\":\"" + templateMessageModel.getUrl() + "\",");
            }
            sb.append("\"data\":{");
            sb.append("\"first\": {\"value\":\"" + templateMessageModel.getFirst() + "\"},");
            for (int i = 0; i < templateMessageModel.getKeywords().size(); i++) {
                sb.append("\"keyword" + (i + 1) + "\": {\"value\":\"" + templateMessageModel.getKeywords().get(i) + "\"},");
            }
            sb.append("\"remark\": {\"value\":\"" + templateMessageModel.getRemark() + "\"}");
            sb.append("}}");

            jsonTemplate =sb.toString();
        }

        // 拼装发送模版消息的url
        String url = template_url.replace("ACCESS_TOKEN", templateMessageModel.getAccessToken());

        // 调用接口发送模版消息
        JSONObject jsonObject = WeixinUtil.httpRequest(url, "POST", jsonTemplate);

        if (null != jsonObject) {
            if (0 != jsonObject.getInt("errcode")) {
                result = jsonObject.getInt("errcode");
                log.error("发送模板消息失败 errcode:"+ result +" errmsg:"+ jsonObject.getString("errmsg") +"****"+jsonTemplate+"****");
            }
        }
        return result;
    }



}
