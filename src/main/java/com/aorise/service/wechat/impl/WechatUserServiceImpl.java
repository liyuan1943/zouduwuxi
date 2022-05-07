package com.aorise.service.wechat.impl;

import com.aorise.exceptions.WechatException;
import com.aorise.model.member.MemberEntity;
import com.aorise.model.wechat.UserInfoModel;
import com.aorise.service.member.MemberService;
import com.aorise.service.wechat.WechatUserService;
import com.aorise.utils.TokenUtils;
import com.aorise.utils.UUIDBits;
import com.aorise.utils.define.ConstDefine;
import com.aorise.utils.wechat.WechatProTokenUtil;
import com.aorise.utils.wechat.WeixinUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by cat on 2017-11-09.
 * 获取用户信息接口实现
 */
@Service
@Transactional(rollbackFor = {Exception.class})
public class WechatUserServiceImpl implements WechatUserService {


    // 换取网页授权access_token（POST）
    public static String getAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=Authorization_code";
    // 拉取用户信息(需scope为 snsapi_userinfo)（GET）
    public static String getUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

    // 小程序获取用户openId（GET）
    public static String getProUserOpenIdUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";

    @Autowired
    private MemberService memberService;

    /**
     * 获取微信小程序用户详细信息
     * @param code
     * @param gender
     * @param nickName
     * @param avatarUrl
     * @return 会员ID
     */
    @Override
    public MemberEntity getWechatProUserInfo(String code, Integer gender, String nickName, String avatarUrl) {
        MemberEntity memberEntity= null;
        UserInfoModel userInfoModel = null;
        // 拼装换取网页授权的url
        String url = getProUserOpenIdUrl.replace("APPID", WechatProTokenUtil.appIdPro).replace("SECRET", WechatProTokenUtil.appSecretPro).replace("JSCODE", code);

        JSONObject jsonObject = WeixinUtil.httpRequest(url, "GET", null);
        // 如果请求成功
        if (null != jsonObject) {
            try {
                userInfoModel = new UserInfoModel();
                userInfoModel.setOpenid(jsonObject.getString("openid"));
                //userInfoModel.setUnionid(jsonObject.getString("unionid"));

            } catch (WechatException e) {
                userInfoModel = null;
                // 获取token失败
                throw new WechatException ("获取微信小程序用户OPENID失败 errcode:{"+ jsonObject.getInt("errcode") +"} errmsg:{"+ jsonObject.getString("errmsg") +"}");
            }

            //查询用户是否存在
            QueryWrapper<MemberEntity> wrapper = new QueryWrapper<>();
            wrapper.eq("openid_pro", userInfoModel.getOpenid());
            memberEntity = memberService.getOne(wrapper);

            //写入用户信息
            boolean iRet = false;
            if(memberEntity == null){
                //新增
                memberEntity = new MemberEntity();
                memberEntity.setCode(UUIDBits.getUUID(ConstDefine.UUID_BITS));
                memberEntity.setUnionid(userInfoModel.getUnionid());
                memberEntity.setOpenidPro(userInfoModel.getOpenid());
                memberEntity.setGender(gender);
                memberEntity.setNickname(nickName);
                memberEntity.setHeadPic(avatarUrl);
                iRet = memberService.save(memberEntity);
            }else {
                //更新
                memberEntity.setGender(gender);
                memberEntity.setNickname(nickName);
                memberEntity.setHeadPic(avatarUrl);
                memberEntity.setIsDelete(ConstDefine.IS_NOT_DELETE);
                iRet = memberService.updateById(memberEntity);
            }
            if(iRet){
                String token= TokenUtils.sign(memberEntity.getId());
                memberEntity.setToken(token);
            }
        }
        return memberEntity;
    }

}
