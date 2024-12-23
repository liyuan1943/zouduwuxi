package com.aorise.service.wechat;

import com.aorise.model.member.MemberEntity;


/**
 * 获取用户信息Service
 * Created by cat on 2017-11-09.
 */
public interface WechatUserService {

    /**
     * 获取微信小程序用户详细信息
     * @param code
     * @param gender
     * @param nickName
     * @param avatarUrl
     * @return 会员ID
     */
    MemberEntity getWechatProUserInfo(String code, Integer gender, String nickName, String avatarUrl);

}
