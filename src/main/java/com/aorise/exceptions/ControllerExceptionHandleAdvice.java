package com.aorise.exceptions;


import com.aorise.utils.StatusDefine;
import com.aorise.utils.json.JsonResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static com.aorise.utils.StatusDefineMessage.getMessage;

/**
 * <p>
 * 全局异常捕获
 * </p>
 *
 * @author shenzhiwei
 * @version v1.0
 * @date Created 2020-04-13 14:25
 * @since 起源于 1.0
 */
@RestControllerAdvice
public class ControllerExceptionHandleAdvice {
    private final static Logger logger = LoggerFactory.getLogger(ControllerExceptionHandleAdvice.class);

    /**
     * 400 - Bad Request
     * @param request  request
     * @param response response
     * @param e e
     * @return 错误信息
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public String handleMissingServletRequestParameterException(HttpServletRequest request, HttpServletResponse response, MissingServletRequestParameterException e) {
        logger.error("controller:" + getClassName(e,request) + ". function:" + getMethodName(e,request) + "");
        logger.error("error:" + e.getMessage());
        return new JsonResponseData(false, "系统错误，请联系管理员！", StatusDefine.PARAM_NUL, e.getMessage(), null).toString();
    }
    /**
     * 空指针异常
     * @param request request
     * @param response response
     * @param  e e
     * @return 错误消息
     */
    @ExceptionHandler( NullPointerException.class )
    public String nullPointerExceptionHandler(HttpServletRequest request, HttpServletResponse response, NullPointerException e) {
        logger.error("controller:" + getClassName(e,request) + ". function:" + getMethodName(e,request) + "");
        logger.error("error:" + e.getMessage());
        return new JsonResponseData(false, "系统错误，请联系管理员！", StatusDefine.NULL_POINTER_ERROR, e.getMessage(), null).toString();
    }

    /**
     * IO异常
     * @param request request
     * @param response response
     * @param e e
     * @return 错误消息
     */
    @ExceptionHandler( IOException.class )
    public String ioExceptionHandler(HttpServletRequest request, HttpServletResponse response, IOException e) {
        logger.error("controller:" + getClassName(e,request) + ". function:" + getMethodName(e,request) + "");
        logger.error("error:" + e.getMessage());
        return new JsonResponseData(false, "系统错误，请联系管理员！", StatusDefine.NULL_POINTER_ERROR, e.getMessage(), null).toString();
    }

    /**
     * 未知方法异常
     * @param request request
     * @param response response
     * @param  e e
     * @return 错误消息
     */
    @ExceptionHandler( NoSuchMethodException.class )
    public String noSuchMethodExceptionHandler(HttpServletRequest request, HttpServletResponse response, NoSuchMethodException e) {
        logger.error("controller:" + getClassName(e,request) + ". function:" + getMethodName(e,request) + "");
        logger.error("error:" + e.getMessage());
        return new JsonResponseData(false, "系统错误，请联系管理员！", StatusDefine.NULL_POINTER_ERROR, e.getMessage(), null).toString();
    }

    /**
     * 数组溢出
     * @param request request
     * @param response response
     * @param e e
     * @return 错误消息
     */
    @ExceptionHandler( IndexOutOfBoundsException.class )
    public String indexOutOfBoundsExceptionHandler(HttpServletRequest request, HttpServletResponse response, IndexOutOfBoundsException e) {
        logger.error("controller:" + getClassName(e,request) + ". function:" + getMethodName(e,request) + "");
        logger.error("error:" + e.getMessage());
        return new JsonResponseData(false, "系统错误，请联系管理员！", StatusDefine.NULL_POINTER_ERROR, e.getMessage(), null).toString();
    }

    /**
     * 栈溢出
     * @param request request
     * @param response response
     * @param e e
     * @return 错误消息
     */
    @ExceptionHandler( {StackOverflowError.class} )
    public String requestStackOverflow(HttpServletRequest request, HttpServletResponse response, Exception e) {
        logger.error("controller:" + getClassName(e,request) + ". function:" + getMethodName(e,request) + "");
        logger.error("error:" + e.getMessage());
        return new JsonResponseData(false, "系统错误，请联系管理员！", StatusDefine.NULL_POINTER_ERROR, e.getMessage(), null).toString();
    }


    /**
     * 请求参数类型不匹配
     * @param request request
     * @param response response
     * @param e e
     * @return 错误消息
     */
    @ExceptionHandler( {IllegalArgumentException.class, IllegalStateException.class} )
    public String illegalArgumentExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {
        logger.error("controller:" + getClassName(e,request) + ". function:" + getMethodName(e,request) + "");
        logger.error("error:" + e.getMessage());
        return new JsonResponseData(false, "系统错误，请联系管理员！", StatusDefine.PARAM_TYPE_ERROR, e.getMessage(), null).toString();
    }


    /**
     * 数据库异常
     * @param request request
     * @param response response
     * @param e e
     * @return 错误消息
     */
    @ExceptionHandler( {DataAccessException.class} )
    public String badSqlExceptionHandler(HttpServletRequest request, HttpServletResponse response, DataAccessException e) {
        logger.error("controller:" + getClassName(e,request) + ". function:" + getMethodName(e,request) + "");
        logger.error("error:" + e.getMessage());
        return new JsonResponseData(false, "系统错误，请联系管理员！", StatusDefine.DB_ERROR, e.getMessage(), null).toString();
    }
    /**
     * 业务逻辑异常
     * @param request request
     * @param response response
     * @param e e
     * @return 错误消息
     */
    @ExceptionHandler( {ServiceException.class} )
    public String badSqlExceptionHandler(HttpServletRequest request, HttpServletResponse response, RuntimeException e) {
        logger.error("controller:" + getClassName(e,request) + ". function:" + getMethodName(e,request) + "");
        logger.error("error:" + e.getMessage());
        return new JsonResponseData(false, e.getMessage(), StatusDefine.SERVICE_ERROR, e.getMessage(), null).toString();
    }
    /**
     * 校验异常
     * @param request     request
     * @param  response  response
     * @param e e
     * @return 错误消息
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public String exceptionHandler(HttpServletRequest request, HttpServletResponse response, MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        String errorMesssage = "";
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMesssage += fieldError.getDefaultMessage() + "!";
        }
        return new JsonResponseData(false, errorMesssage, StatusDefine.DATA_FORMAT_ERROR, errorMesssage, null).toString();
    }
    /**
     * 校验异常
     * @param request request
     * @param response response
     * @param e e
     * @return 错误消息
     */
    @ExceptionHandler(value = BindException.class)
    public String validationExceptionHandler(HttpServletRequest request, HttpServletResponse response, BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        String errorMesssage = "";
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMesssage += fieldError.getDefaultMessage() + "!";
        }
        return new JsonResponseData(false, errorMesssage, StatusDefine.DATA_FORMAT_ERROR, errorMesssage, null).toString();
    }
    /**
     * 校验异常
     * @param request request
     * @param response response
     * @param e e
     * @return 错误消息
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public String constraintViolationExceptionHandler(HttpServletRequest request, HttpServletResponse response, ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        Iterator<ConstraintViolation<?>> iterator = constraintViolations.iterator();
        List<String> msgList = new ArrayList<>();
        while (iterator.hasNext()) {
            ConstraintViolation<?> cvl = iterator.next();
            msgList.add(cvl.getMessageTemplate());
        }
        return new JsonResponseData(false, String.join(",",msgList), StatusDefine.DATA_FORMAT_ERROR, String.join(",",msgList), null).toString();
    }
    //系统异常

    /**
     * 系统异常
     * @param request request
     * @param response response
     * @param e e
     * @return 错误消息
     */
    @ExceptionHandler( {Exception.class} )
    public String exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {
        logger.error("controller:" + getClassName(e,request) + ". function:" + getMethodName(e,request) + "");
        logger.error("error:" + e.getMessage());
        return new JsonResponseData(false, getMessage(StatusDefine.SYS_ERROR), StatusDefine.SYS_ERROR, getMessage(StatusDefine.SYS_ERROR), null).toString();
    }

//    /**
//     * 权限不足
//     * @param request request
//     * @param response response
//     * @param e e
//     * @return 错误消息
//     */
//    @ExceptionHandler( AuthUserException.class )
//    public String handleAuth(HttpServletRequest request, HttpServletResponse response, AuthUserException e) {
//        logger.error("controller:" + getClassName(e,request) + ". function:" + getMethodName(e,request) + "");
//        logger.error("error:" + e.getMessage());
//        return new Message(StatusDefine.PERMISSION_DENIED, StatusDefineMessage.GetMessage(StatusDefine.PERMISSION_DENIED), "");
//    }
//
//    @ExceptionHandler( ExpiredJwtException.class )
//    public String handleExpiredJwtException(HttpServletRequest request, HttpServletResponse response, ExpiredJwtException e) {
//        return new Message(StatusDefine.U_LOGIN_OUTTIME, StatusDefineMessage.GetMessage(StatusDefine.U_LOGIN_OUTTIME), "");
//    }

    private String getClassName(Exception e, HttpServletRequest request) {
        try {
            return ((MethodArgumentTypeMismatchException) e).getParameter().getMethod().getName();
        } catch (Exception ex) {
            return "";
        }
    }

    private String getMethodName(Exception e, HttpServletRequest request) {
        try {
            return ((MethodArgumentTypeMismatchException) e).getParameter().getContainingClass().getName();
        } catch (Exception ex) {
            return "";
        }
    }
}
