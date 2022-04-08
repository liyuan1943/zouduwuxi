package com.aorise.controller.system;

import com.aorise.exceptions.DataValidationException;
import com.aorise.model.system.SysUserModel;
import com.aorise.service.system.SystemService;
import com.aorise.utils.StatusDefine;
import com.aorise.utils.StatusDefineMessage;
import com.aorise.utils.Utils;
import com.aorise.utils.json.JsonResponseData;
import com.aorise.utils.validation.DataValidation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author cat
 * @Description 用户管理相关接口
 * @date Created in 2018/9/21 14:14
 * @modified  By:
 */
@RestController
@Api(value="用户管理相关接口",tags ="用户管理相关接口" )
public class UserController {

    @Autowired
    private SystemService systemService;

    @Autowired
    private DataValidation dataValidation;

    /**
     * 日志打印器
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 修改用户-修改密码
     * HTTP 方式：POST
     * API 路径：/api/user/updateUserPwd
     * 方法名：updateUserPwd
     * 方法返回类型：String
     */
    @ApiOperation(value = "修改用户-修改密码", httpMethod = "POST", response = String.class, notes = "系统管理-用户管理-修改用户-修改密码")
    @RequestMapping(value="/api/user/updateUserPwd", method= RequestMethod.POST)
    public String updateUserPwd(@ApiParam(value = "用户id", required = true) @RequestParam(value = "userId", required = true) String userId,
                                     @ApiParam(value = "原密码", required = true) @RequestParam(value = "oldPassWord", required = true) String oldPassWord,
                                     @ApiParam(value = "新密码", required = true) @RequestParam(value = "newPassWord", required = true) String newPassWord,
                                     @ApiParam(value = "确认新密码", required = true) @RequestParam(value = "affirmNewPassWord", required = true) String affirmNewPassWord) {

            //参数判断
            dataValidation.chekeNotempty(userId, "id不能为空");
            dataValidation.chekeNotempty(oldPassWord, "原密码不能为空");
            dataValidation.chkLength(newPassWord, 6, 16, "新密码长度不正确");
            //实体封装
            SysUserModel model = systemService.findObject(Integer.parseInt(userId));
            if (!newPassWord.equals(affirmNewPassWord)) {
                return new JsonResponseData(false, StatusDefineMessage.getMessage(StatusDefine.U_PWD_TWO_INCONFORMITY), StatusDefine.U_PWD_TWO_INCONFORMITY,
                        "", "两次密码不一致").toString();
            }
            if (model == null) {
                return new JsonResponseData(false, StatusDefineMessage.getMessage(StatusDefine.U_INEXISTENCE), StatusDefine.U_INEXISTENCE,
                        "", null).toString();
            }
            if(!Utils.getMd5DigestAsHex(oldPassWord).equals(model.getPassWord())){
                return new JsonResponseData(false, StatusDefineMessage.getMessage(StatusDefine.U_PWD_OLD_FAILED), StatusDefine.U_PWD_OLD_FAILED,
                        "", "原密码不正确").toString();
            }
            model.setPassWord(Utils.getMd5DigestAsHex(newPassWord));

            //调用接口
            int ret = systemService.editeObject(model);
            //返回参数判断
            if (ret > 0) {
                return new JsonResponseData(true, StatusDefineMessage.getMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS,
                        "", ret).toString();
            } else {
                return new JsonResponseData(false, StatusDefineMessage.getMessage(StatusDefine.FAILURE), StatusDefine.FAILURE,
                        "", null).toString();
            }
    }

    /**
     * 根据id查询用户
     * HTTP 方式：GET
     * API 路径：/api/user/userId/{userId}
     * 方法名：findObjectByuserid
     * 方法返回类型：String
     */
    @ApiOperation(value = "根据id查询用户", httpMethod = "GET", response = String.class, notes = "系统管理-用户管理-编辑用户信息展示")
    @RequestMapping(value = "/api/user/userId/{userId}", method = RequestMethod.GET, produces = "application/json")
    public String findObjectByuserid(@ApiParam(value = "用户id", required = true) @PathVariable(value = "userId", required = true) String userId) {

            //参数判断
            dataValidation.chekeNotempty(userId, "id不能为空");

            //调用接口
            SysUserModel model = systemService.findObject(Integer.parseInt(userId));
            return new JsonResponseData(true, StatusDefineMessage.getMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS,
                    "", model).toString();


    }




}
