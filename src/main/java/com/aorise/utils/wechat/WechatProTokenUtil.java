package com.aorise.utils.wechat;

import com.aorise.exceptions.WechatException;
import com.aorise.mapper.token.TokenMapper;
import com.aorise.model.wechat.AccessTokenModel;
import com.aorise.utils.Utils;
import com.aorise.utils.define.ConstDefine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author cat
 * @Description 定时获取微信小程序access_token的线程
 * @date   2018-04-12  10:00
 * @modified By:
 */
@Component
@Configuration
public class WechatProTokenUtil {
    private static Logger log = LoggerFactory.getLogger(WechatProTokenUtil.class);
    @Autowired
    private TokenMapper tokenMapper;

    /**
     * appId
     */
    public static String appIdPro;
    /**
     * appSecret
     */
    public static String appSecretPro;
    /**
     * updateFlag
     */
    public static int updateFlag;

    @Value("${wechat.appId_pro}")
    public void setAppIdPro(String appIdPro) {
        WechatProTokenUtil.appIdPro = appIdPro;
    }

    @Value("${wechat.appSecret_pro}")
    public void setAppSecretPro(String appSecretPro) {
        WechatProTokenUtil.appSecretPro = appSecretPro;
    }

    @Value("${wechat.update_flag}")
    public void setUpdateFlag(int updateFlag) {
        WechatProTokenUtil.updateFlag = updateFlag;
    }

    /**
     * 第三方用户唯一凭证
     */
    private static AccessTokenModel accessToken = null;

    @Scheduled(fixedDelay = 2*3600*1000)
    //7200秒执行一次
    public void getToken() {
        if(updateFlag == 1) {
            accessToken = WeixinUtil.getAccessToken(appIdPro, appSecretPro);
            if (accessToken != null) {
                /**把微信token保存在数据库*/
                int iRet = 0;
                try {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", ConstDefine.TOKEN_ID_WECHAT_PRO);
                    map.put("token", accessToken.getToken());
                    map.put("expiresIn", accessToken.getExpiresIn());
                    map.put("editIp", Utils.getLocalIp());
                    iRet = tokenMapper.updateToken(map);
                } catch (DataAccessException e) {
                    throw new WechatException("保存微信小程序token到数据库失败");
                }
                if (iRet > 0) {
                    log.info("微信小程序token获取成功，accessToken:" + accessToken.getToken());
                } else {
                    log.error("微信小程序token获取失败");
                }
            } else {
                log.error("微信小程序token获取失败");
            }
        }
    }

}
