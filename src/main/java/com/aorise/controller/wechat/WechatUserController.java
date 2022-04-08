package com.aorise.controller.wechat;

import com.aorise.exceptions.ServiceException;
import com.aorise.exceptions.WechatException;
import com.aorise.service.wechat.WechatUserService;
import com.aorise.utils.StatusDefine;
import com.aorise.utils.StatusDefineMessage;
import com.aorise.utils.json.JsonResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author cat
 * @Description 获取用户信息控制器类
 * @date   2018-04-08  18:06
 * @modified By:
 */
@RestController
@Api(value="获取微信用户信息",tags ="获取微信用户信息" )
public class WechatUserController {

    private static Logger logger = LoggerFactory.getLogger(WechatUserController.class);

    @Autowired
    private WechatUserService wechatUserService;

    /**
     * 小程序用户登录,返回用户memberId
     * HTTP 方式：GET
     * API 路径：/api/wechatUser/getWechatProOpenId
     * 方法名：getWechatProOpenId
     * 方法返回类型：String
     * 方法异常：WechatException
     * @author  cat
     * @date   2018/04/08
     */
    @ApiOperation(value = "小程序用户登录,返回用户memberId", notes = "小程序用户登录,返回用户memberId", produces = "application/json")
    @RequestMapping(value = "/api/wechatUser/getWechatProOpenId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getWechatProOpenId(@ApiParam(value = "code", required = true) @RequestParam(value = "code", required = true) String code,
                                     @ApiParam(value = "性别", required = true) @RequestParam(value = "gender", required = true) Integer gender,
                                     @ApiParam(value = "昵称", required = true) @RequestParam(value = "nickName", required = true) String nickName,
                                     @ApiParam(value = "头像", required = true) @RequestParam(value = "avatarUrl", required = true) String avatarUrl) {
        logger.debug("Request RESTful API:getWechatProOpenId");
        logger.debug("code:"+code);
        logger.debug("gender:"+gender);
        logger.debug("nickName:"+nickName);
        logger.debug("avatarUrl:"+avatarUrl);

            Integer memberId = wechatUserService.getWechatProUserInfo(code,gender,nickName,avatarUrl);

            logger.debug("memberId:"+memberId);
            return new JsonResponseData(true, StatusDefineMessage.getMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS, null, memberId).toString();

    }

}