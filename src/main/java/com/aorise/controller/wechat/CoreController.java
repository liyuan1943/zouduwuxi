package com.aorise.controller.wechat;

import com.aorise.exceptions.WechatException;
import com.aorise.utils.StatusDefine;
import com.aorise.utils.StatusDefineMessage;
import com.aorise.utils.json.JsonResponseData;
import com.aorise.utils.wechat.SignUtil;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cat
 * @Description 微信校验控制器类
 * @date   2018-04-08  18:06
 * @modified By:
 */
@RestController
@RequestMapping("/core")
@Api(value="CoreController RESTful Service API",tags ="CoreController RESTful Service API" )
public class CoreController {

    private static Logger logger = LoggerFactory.getLogger(CoreController.class);


    /**
     * 验证是否来自微信服务器的消息
     * HTTP 方式：GET
     * API 路径：/core
     * 方法名：checkSignature
     * 方法返回类型：String
     * 方法异常：WechatException
     * @author  cat
     * @date   2018/04/08
     */
    @RequestMapping(value = "",method = RequestMethod.GET, produces = "application/xml; charset=UTF-8")
    public String checkSignature(@RequestParam(name = "signature" ,required = false) String signature  ,
                                 @RequestParam(name = "nonce",required = false) String  nonce ,
                                 @RequestParam(name = "timestamp",required = false) String  timestamp ,
                                 @RequestParam(name = "echostr",required = false) String  echostr) {

            /**通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败*/
            if (SignUtil.checkSignature(signature, timestamp, nonce)) {
                logger.info("接入成功");
                return echostr;
            }
            logger.error("接入失败");
            return "";
    }

    
}