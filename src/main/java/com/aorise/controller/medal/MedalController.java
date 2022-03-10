package com.aorise.controller.medal;

import com.aorise.model.medal.MedalEntity;
import com.aorise.model.medal.MemberMedalEntity;
import com.aorise.service.medal.MedalService;
import com.aorise.service.common.UploadService;
import com.aorise.utils.StatusDefine;
import com.aorise.utils.StatusDefineMessage;
import com.aorise.utils.define.ConstDefine;
import com.aorise.utils.json.JsonResponseData;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 勋章 控制器
 *
 * @author cat
 * @version 1.0
 */
@RestController
@Api(value = "勋章模块", tags = "勋章模块")
public class MedalController {

    //日志打印器
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 勋章服务接口
     */
    @Autowired
    MedalService medalService;
    @Autowired
    UploadService uploadService;

    /**
     * 查询全部勋章信息
     * HTTP 方式：GET
     * API 路径：/api/medal
     * 方法名：getAllMedal
     * 方法返回类型：String
     */
    @ApiOperation(value = "查询全部勋章信息", notes = "查询全部勋章信息", produces = "application/json")
    @RequestMapping(value = "/api/medal", method = RequestMethod.GET)
    public String getAllMedal(@ApiParam(value = "年份", required = false) @RequestParam(value = "year", required = false) String year) {
        logger.debug("Request RESTful API:getAllMedal");
        logger.debug("year：" + year);

        List<MedalEntity> medalEntities;
        try {
            //装载查询条件
            QueryWrapper<MedalEntity> entity = new QueryWrapper<>();
            entity.eq("year", year);
            entity.eq("is_delete", ConstDefine.IS_NOT_DELETE);
            entity.orderByDesc("year");
            entity.orderByAsc("scenic_id");
            medalEntities = medalService.list(entity);
            return new JsonResponseData(true, StatusDefineMessage.getMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS, "", medalEntities).toString();
        } catch (Exception e) {
            return new JsonResponseData(false, StatusDefineMessage.getMessage(StatusDefine.FAILURE), StatusDefine.FAILURE, "", "").toString();
        }
    }

    /**
     * 根据ID查询勋章信息
     * HTTP 方式：GET
     * API 路径：/api/medal/id/{id}
     * 方法名：getMedalById
     * 方法返回类型：String
     */
    @ApiOperation(value = "根据ID查询勋章信息", notes = "根据ID查询勋章信息", produces = "application/json")
    @RequestMapping(value = "/api/medal/id/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getMedalById(@ApiParam(value = "主键ID", required = true) @PathVariable(value = "id", required = true) Integer id) {
        logger.debug("Request RESTful API:getMedalById");
        logger.debug("id：" + id);
        MedalEntity medalEntity = null;
        try {
            QueryWrapper<MedalEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", id);
            queryWrapper.eq("is_delete", ConstDefine.IS_NOT_DELETE);
            medalEntity = medalService.getOne(queryWrapper);
        } catch (Exception e) {

            return new JsonResponseData(false, StatusDefineMessage.getMessage(StatusDefine.FAILURE), StatusDefine.FAILURE, "", "").toString();
        }
        return new JsonResponseData(true, StatusDefineMessage.getMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS, "", medalEntity).toString();
    }

    /**
     * 新增勋章信息
     * HTTP 方式：POST
     * API 路径：/api/medal/addMedal
     * 方法名：addMedal
     * 方法返回类型：String
     */
    @ApiOperation(value = "新增勋章信息", notes = "新增勋章信息", produces = "application/json")
    @RequestMapping(value = "/api/medal/addMedal", method = RequestMethod.POST)
    public String addMedal(@RequestBody @Validated MedalEntity medalEntity) {
        logger.debug("Request RESTful API:addMedal");
        logger.debug("medal：" + medalEntity);

        try {

            int iRet = medalService.addMedal(medalEntity);
            if (iRet > 0) {
                return new JsonResponseData(true, StatusDefineMessage.getMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS, "", medalEntity.getId()).toString();
            } else {
                return new JsonResponseData(false, StatusDefineMessage.getMessage(StatusDefine.FAILURE), StatusDefine.FAILURE, "", "").toString();
            }
        } catch (Exception e) {

            return new JsonResponseData(false, StatusDefineMessage.getMessage(StatusDefine.FAILURE), StatusDefine.FAILURE, "", "").toString();
        }
    }

    /**
     * 修改勋章信息
     * HTTP 方式：POST
     * API 路径：/api/medal/updateMedal
     * 方法名：updateMedal
     * 方法返回类型：String
     */
    @ApiOperation(value = "修改勋章信息", notes = "修改勋章信息", produces = "application/json")
    @RequestMapping(value = "/api/medal/updateMedal", method = RequestMethod.POST)
    public String updateMedal(@RequestBody @Validated MedalEntity medalEntity, HttpServletRequest request) {
        logger.debug("Request RESTful API:updateMedal");
        logger.debug("medal：" + medalEntity);

        try {
            int iRet = medalService.updateMedal(medalEntity, request);
            if (iRet > 0) {
                return new JsonResponseData(true, StatusDefineMessage.getMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS, "", "").toString();
            } else {
                return new JsonResponseData(false, StatusDefineMessage.getMessage(StatusDefine.FAILURE), StatusDefine.FAILURE, "", "").toString();
            }
        } catch (Exception e) {

            return new JsonResponseData(false, StatusDefineMessage.getMessage(StatusDefine.FAILURE), StatusDefine.FAILURE, "", "").toString();
        }
    }

    /**
     * 删除勋章信息
     * HTTP 方式：POST
     * API 路径：/api/medal/id/{id}
     * 方法名：deleteMedal
     * 方法返回类型：String
     */
    @ApiOperation(value = "删除勋章信息", notes = "删除勋章信息", produces = "application/json")
    @RequestMapping(value = "/api/medal/id/{id}", method = RequestMethod.POST)
    public String deleteMedal(@ApiParam(value = "主键ID", required = true) @PathVariable(value = "id", required = true) Integer id,
                              HttpServletRequest request) {
        logger.debug("Request RESTful API:deleteMedal");
        logger.debug("id：" + id);

        try {
            MedalEntity medalEntity = new MedalEntity();
            medalEntity.setIsDelete(ConstDefine.IS_DELETE);
            medalEntity.setId(id);
            boolean bool = medalService.updateById(medalEntity);


            if (bool) {
                //删除图片文件
                MedalEntity b = medalService.getById(id);
                uploadService.deletefile(b.getPic(), request);
                return new JsonResponseData(true, StatusDefineMessage.getMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS, "", "").toString();
            } else {
                return new JsonResponseData(false, StatusDefineMessage.getMessage(StatusDefine.FAILURE), StatusDefine.FAILURE, "", "").toString();
            }
        } catch (Exception e) {

            return new JsonResponseData(false, StatusDefineMessage.getMessage(StatusDefine.FAILURE), StatusDefine.FAILURE, "", "").toString();
        }
    }

    /**
     * 根据会员ID查询获得的勋章信息
     * HTTP 方式：GET
     * API 路径：/api/medal/getMedalByMemberId
     * 方法名：getMedalByMemberId
     * 方法返回类型：String
     */
    @ApiOperation(value = "根据会员ID查询获得的勋章信息", notes = "根据会员ID查询获得的勋章信息", produces = "application/json")
    @RequestMapping(value = "/api/medal/getMedalByMemberId", method = RequestMethod.GET)
    public String getMedalByMemberId(@ApiParam(value = "会员ID", required = false) @RequestParam(value = "memberId", required = true) String memberId) {
        logger.debug("Request RESTful API:getMedalByMemberId");
        logger.debug("memberId：" + memberId);

        List<MedalEntity> medalEntities;
        try {
            medalEntities = medalService.getMedalByMemberId(memberId);
            return new JsonResponseData(true, StatusDefineMessage.getMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS, "", medalEntities).toString();
        } catch (Exception e) {
            return new JsonResponseData(false, StatusDefineMessage.getMessage(StatusDefine.FAILURE), StatusDefine.FAILURE, "", "").toString();
        }
    }

}
