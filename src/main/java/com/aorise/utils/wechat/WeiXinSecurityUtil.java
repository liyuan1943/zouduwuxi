package com.aorise.utils.wechat;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HttpsURLConnection;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * @Author: yulu
 * @Date: 2021/8/17 下午3:47
 * @Desc: 微信安全工具类
 * @Version 1.0
 */
public class WeiXinSecurityUtil {
    private static Logger log = LoggerFactory.getLogger(WeixinUtil.class);
    /**
     * 内容安全检测接口路径
     */
    private static String MSG_SEC_URL = "https://api.weixin.qq.com/wxa/msg_sec_check?access_token=";
    /**
     * 图片检测接口路径
     */
    private static String IMG_SEC_URL = "https://api.weixin.qq.com/wxa/img_sec_check?access_token=";

    /**
     * 小程序验证文字是否违规.
     * 创建时间 2021年12月3日 下午2:12:29
     * @author yulu
     * @param openid	用户id
     * @param txt		文字内容
     * @return true 不违规
     */
    public static boolean securityMsgSecCheck(String openid, String txt) {
        if (IsNullUtil.strsIsNull(openid, txt)) {
            return false;
        }

        String token = getToken();
        if (token == null) {
            log.error("检查一段文本是否含有违法违规内容失败,无法获取到token");
            return false;
        }

        HttpsURLConnection huc = null;
        try {
            // 这里的 SECURITY_MSG_SEC_CHECK 是接口地址(不带参数的),后面追加一个token
            huc = (HttpsURLConnection) new URL(MSG_SEC_URL + token).openConnection();
            huc.setRequestMethod("POST");
            huc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            huc.setDoInput(true);
            huc.setDoOutput(true);

            JSONObject param = new JSONObject();
            param.put("version", "2");
            param.put("openid", openid);
            param.put("scene", "1");
            param.put("content", txt);
            huc.getOutputStream().write(param.toString().getBytes());

            String data = new String(String.valueOf(huc.getInputStream().read()));
            JSONObject result = (JSONObject) JSONObject.parse(data);
            String errcode = String.valueOf(result.get("errcode"));
            String errmsg = (String) result.get("errmsg");
            if (!"0".equals(errcode)) {
                log.error("小程序检测文字是否违规失败: ".concat(result.toJSONString()));
                return false;
            } else {
                JSONObject r = result.getJSONObject("result");
                if ("pass".equals(r.getString("suggest"))) {
                    return true;
                } else {
                    log.error("小程序检测文字结果违规,文字=%s, result=%s", txt, result);
                    return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("小程序检测文字是否违规失败失败: %s", e.getMessage());
        } finally {
            if (huc != null) {
                huc.disconnect();
            }
        }
        return false;
    }


    /**
     * 小程序验证图片是否违规.
     * 创建时间 2021年12月3日 下午5:11:42
     * @author yulu
     * @param path 图片路径
     * @return true不违规
     */
    public static boolean securityImgSecCheck(String path) {
        if (path == null) {
            return false;
        }

        String token = getToken();
        if (token == null) {
            log.error("检查图片是否含有违法违规内容失败,无法获取到token");
            return false;
        }

        RestTemplate http = new RestTemplate();

        HttpHeaders heads = new HttpHeaders();
        heads.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        // 这个地方只能使用 FileSystemResource 的形式,我直接用 byte[] 的话,接口给我返回的 47001 参数错误
        param.add("media", new FileSystemResource(new File(path)));

        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(param, heads);
        String data = http.postForObject(IMG_SEC_URL + token, entity, String.class);

        JSONObject result = (JSONObject) JSONObject.parse(data);
        System.out.println(result);

        String errcode = String.valueOf(result.get("errcode"));
        String errmsg = (String) result.get("errmsg");
        if (!"0".equals(errcode)) {
            return false;
        } else {
            return true;
        }
    }
}
