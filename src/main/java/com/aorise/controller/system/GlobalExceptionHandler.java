package com.aorise.controller.system;
import com.aorise.exceptions.LoginOvertimeException;
import com.aorise.utils.StatusDefine;
import com.aorise.utils.StatusDefineMessage;
import com.aorise.utils.json.JsonResponseData;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author cat
 * @Description   全局异常处理
 * @date  Created in 2018/8/10 15:44
 * @modified By:
 */

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {


    @ExceptionHandler(value=AuthorizationException.class)
    public String allAuthorizationExceptionHandler(HttpServletRequest request,
                                      Exception exception) throws Exception
    {
        return new JsonResponseData(false, StatusDefineMessage.getMessage(StatusDefine.PERMISSIONDENIED), StatusDefine.PERMISSIONDENIED,
                "", "").toString();
    }


    @ExceptionHandler(value=LoginOvertimeException.class)
    public String LoginOvertimeExceptionHandler(HttpServletRequest request,
                                                   Exception exception) throws Exception
    {
        return new JsonResponseData(false, StatusDefineMessage.getMessage(StatusDefine.U_LOGIN_OUTTIME), StatusDefine.U_LOGIN_OUTTIME,
                "", "").toString();
    }
}
