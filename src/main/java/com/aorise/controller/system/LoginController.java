package com.aorise.controller.system;

import com.aorise.mapper.system.SysUserMapper;
import com.aorise.model.system.LoginUserModel;
import com.aorise.model.system.SysUserModel;
import com.aorise.utils.StatusDefine;
import com.aorise.utils.StatusDefineMessage;
import com.aorise.utils.TokenUtils;
import com.aorise.utils.Utils;
import com.aorise.utils.json.JsonResponseData;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "登录登出相关接口", tags = "登录登出相关接口")
@RestController
public class LoginController {

    @Autowired
    SysUserMapper sysUserMapper;

    @ApiOperation(value = "用户登录", httpMethod = "POST", response = String.class, notes = "用户登录")
    @RequestMapping(value = "/api/login", method = RequestMethod.POST, produces = "application/json")
    public String login(@RequestBody @Validated LoginUserModel loginUserModel) {
        if(StringUtils.isNotBlank(loginUserModel.getUsername())  && StringUtils.isNotBlank(loginUserModel.getPassword()) ) {
            QueryWrapper<SysUserModel> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_name", loginUserModel.getUsername());
            queryWrapper.eq("password", Utils.getMd5DigestAsHex(loginUserModel.getPassword()));
            SysUserModel sysUserModel = sysUserMapper.selectOne(queryWrapper);

            //用户不存在
            if(sysUserModel == null){
                return new JsonResponseData(true, StatusDefineMessage.getMessage(StatusDefine.U_PWD_FAILED), StatusDefine.U_PWD_FAILED, "", null).toString();
            }

            String token= TokenUtils.sign(sysUserModel.getId());
            sysUserModel.setToken(token);
            return new JsonResponseData(true, StatusDefineMessage.getMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS, "", sysUserModel).toString();
        }else {
            return new JsonResponseData(true, StatusDefineMessage.getMessage(StatusDefine.FAILURE), StatusDefine.FAILURE, "", null).toString();
        }
    }

    @RequestMapping(value = "/api/403", method = RequestMethod.GET)
    public String unauthorizedRole() {
        return new JsonResponseData(false, StatusDefineMessage.getMessage(StatusDefine.PERMISSIONDENIED), StatusDefine.PERMISSIONDENIED,
                "", null).toString();
    }

    @RequestMapping(value = "/api/unauth", method = RequestMethod.GET)
    public String unauth() {
        return new JsonResponseData(false, StatusDefineMessage.getMessage(StatusDefine.U_LOGIN_OUTTIME), StatusDefine.U_LOGIN_OUTTIME,
                "", null).toString();
    }

    @ApiOperation(value = "用户登出", httpMethod = "GET", response = String.class, notes = "用户登出")
    @RequestMapping(value = "/api/logout", method = RequestMethod.GET)
    public String logOut() {

        return new JsonResponseData(true, StatusDefineMessage.getMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS, "", null).toString();
    }

}
