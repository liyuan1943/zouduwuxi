package com.aorise.controller.system;

import com.aorise.model.system.LoginUserModel;
import com.aorise.model.system.SysUserModel;
import com.aorise.utils.StatusDefine;
import com.aorise.utils.StatusDefineMessage;
import com.aorise.utils.Utils;
import com.aorise.utils.json.JsonResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.dao.DataAccessException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(value = "登录登出相关接口", tags = "登录登出相关接口")
@RestController
public class LoginController {

    @ApiOperation(value = "用户登录", httpMethod = "POST", response = String.class, notes = "用户登录")
    @RequestMapping(value = "/api/login", method = RequestMethod.POST, produces = "application/json")
    public String login(@RequestBody @Validated LoginUserModel loginUserModel) {

        Subject currentUser = SecurityUtils.getSubject();//获取当前sbuject
        UsernamePasswordToken token = new UsernamePasswordToken(loginUserModel.getUsername(), Utils.getMd5DigestAsHex(loginUserModel.getPassword()));
        token.setRememberMe(true);
        try {
            //执行登陆
            currentUser.login(token);
            SecurityUtils.getSubject().getSession().setTimeout(1800000);//设置超时时间 单位ms
            SysUserModel user = (SysUserModel) currentUser.getPrincipal();
            user.setToken((String) currentUser.getSession().getId());
            user.setIsInitialPwd(2);//不是初始密码
            if (user.getUserName().length() > 6 && user.getPassWord()
                    .equals(Utils.getMd5DigestAsHex(user.getUserName()
                            .substring(user.getUserName().length() - 6)))) {//判断是否初始密码
                user.setIsInitialPwd(1);
            }
            return new JsonResponseData(true, StatusDefineMessage.getMessage(StatusDefine.SUCCESS),
                    StatusDefine.SUCCESS, "", user).toString();

        } catch (IncorrectCredentialsException ae) {//密码错误
            return new JsonResponseData(false, StatusDefineMessage.getMessage(StatusDefine.U_PWD_FAILED), StatusDefine.U_PWD_FAILED,
                    "", null).toString();
        } catch (UnknownAccountException e) {//用户不存在
            return new JsonResponseData(false, StatusDefineMessage.getMessage(StatusDefine.U_INEXISTENCE), StatusDefine.U_INEXISTENCE,
                    "", null).toString();
        } catch (DisabledAccountException e) {//用户冻结
            return new JsonResponseData(false, StatusDefineMessage.getMessage(StatusDefine.U_UNACTIVE), StatusDefine.U_UNACTIVE,
                    "", null).toString();
        } catch (DataAccessException e) {
            return new JsonResponseData(false, StatusDefineMessage.getMessage(StatusDefine.DB_ERROR),
                    StatusDefine.DB_ERROR, "", null).toString();
        } catch (Exception e) {

            return new JsonResponseData(false, StatusDefineMessage.getMessage(StatusDefine.SYS_ERROR), StatusDefine.SYS_ERROR,
                    "", null).toString();
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
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser != null) {
            currentUser.logout();
        }
        return new JsonResponseData(true, StatusDefineMessage.getMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS, "", null).toString();
    }

}
