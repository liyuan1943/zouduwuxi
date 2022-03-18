package com.aorise.utils.define;

/**
 * @author cat
 * @Description 自定义常量
 * @date Created in 2018-05-24 11:04
 * @modified By:
 */
public interface ConstDefine {

    //删除状态
    //正常
    int IS_NOT_DELETE = 1;
    //删除
    int IS_DELETE = -1;

    //微信公众号token
    int TOKEN_ID_WECHAT = 1;
    //微信小程序token
    int TOKEN_ID_WECHAT_PRO = 2;
    //网页应用token
    int TOKEN_ID_WECHAT_WEB = 3;

    //UUID随机数位数
    int UUID_BITS = 20;
    int UUID_ORDER = 32;

    //活动状态：1未开始，2进行中，3已结束
    int ACTIVITY_WAIT = 1;
    int ACTIVITY_OPENNING = 2;
    int ACTIVITY_END = 3;

    //是否终点：1是，2否
    int IS_DESTINATION_YES = 1;
    int IS_DESTINATION_NO = 2;

    //正式环境服务地址
    String PRO_WEBSITE = "https://www.tindoe.com/apiserver";

}
