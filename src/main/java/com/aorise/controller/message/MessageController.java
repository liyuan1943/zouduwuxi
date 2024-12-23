package com.aorise.controller.message;

import com.aorise.model.message.MessageEntity;
import com.aorise.service.message.MessageService;
import com.aorise.utils.StatusDefine;
import com.aorise.utils.StatusDefineMessage;
import com.aorise.utils.define.ConstDefine;
import com.aorise.utils.json.JsonResponseData;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 留言 控制器
 *
 * @version 1.0
 * @author:cat
 */
@RestController
@Api(value = "留言模块", tags = "留言模块")
public class MessageController {

    //日志打印器
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MessageService messageService;

    /**
     * 分页查询留言信息
     * HTTP 方式：GET
     * API 路径：/api/message/pageIndex/{pageIndex}/pageNum/{pageNum}
     * 方法名：getMessageByPage
     * 方法返回类型：String
     */
    @ApiOperation(value = "分页查询留言信息", notes = "分页查询留言信息", produces = "application/json")
    @RequestMapping(value = "/api/message/pageIndex/{pageIndex}/pageNum/{pageNum}", method = RequestMethod.GET)
    public String getMessageByPage(@ApiParam(value = "景点ID", required = false) @RequestParam(value = "scenicId", required = false) Integer scenicId,
                                   @ApiParam(value = "关键字", required = false) @RequestParam(value = "key", required = false) String key,
                                   @ApiParam(value = "页索引", required = true) @PathVariable(value = "pageIndex", required = true) Integer pageIndex,
                                   @ApiParam(value = "页大小", required = true) @PathVariable(value = "pageNum", required = true) Integer pageNum) {
        logger.debug("Request RESTful API:getMessageByPage");
        logger.debug("scenicId：" + scenicId);
        logger.debug("key：" + key);
        logger.debug("pageIndex：" + pageIndex);
        logger.debug("pageNum：" + pageNum);

        Page<MessageEntity> page = new Page<>(pageIndex, pageNum);

        //装载查询条件
        QueryWrapper<MessageEntity> entity = new QueryWrapper<>();
        if (scenicId != null) {
            entity.eq("scenic_id", scenicId);
        }
        if (StringUtils.isNotBlank(key)) {
            entity.like("content", key);
        }
        entity.eq("is_delete", ConstDefine.IS_NOT_DELETE);
        entity.orderByDesc("top_time");
        entity.orderByDesc("create_date");

        page = messageService.getMessageByPage(page, entity);

        return new JsonResponseData(true, StatusDefineMessage.getMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS, "", page).toString();
    }

    /**
     * 新增留言信息
     * HTTP 方式：POST
     * API 路径：/api/message
     * 方法名：addMessage
     * 方法返回类型：String
     */
    @ApiOperation(value = "新增留言信息", notes = "新增留言信息", produces = "application/json")
    @RequestMapping(value = "/api/message", method = RequestMethod.POST)
    public String addMessage(@RequestBody @Validated MessageEntity messageEntity,
                             HttpServletRequest request) {
        logger.debug("Request RESTful API:addMessage");
        logger.debug("message：" + messageEntity);

        int bol = messageService.addMessage(messageEntity,request);
        if (bol > 0) {
            return new JsonResponseData(true, StatusDefineMessage.getMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS, "", messageEntity.getId()).toString();
        } else {
            return new JsonResponseData(false, StatusDefineMessage.getMessage(StatusDefine.FAILURE), StatusDefine.FAILURE, "", "").toString();
        }
    }

    /**
     * 删除留言信息
     * HTTP 方式：POST
     * API 路径：/api/message/id/{id}
     * 方法名：deleteMessage
     * 方法返回类型：String
     */
    @ApiOperation(value = "删除留言信息", notes = "删除留言信息", produces = "application/json")
    @RequestMapping(value = "/api/message/id/{id}", method = RequestMethod.POST)
    public String deleteMessage(@ApiParam(value = "主键ID", required = true) @PathVariable(value = "id", required = true) Integer id,
                                HttpServletRequest request) {
        logger.debug("Request RESTful API:deleteMessage");
        logger.debug("id：" + id);

        int bool = messageService.deleteMessage(id, request);
        if (bool > 0) {
            return new JsonResponseData(true, StatusDefineMessage.getMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS, "", "").toString();
        } else {
            return new JsonResponseData(false, StatusDefineMessage.getMessage(StatusDefine.FAILURE), StatusDefine.FAILURE, "", "").toString();
        }
    }

    /**
     * 留言信息置顶、取消置顶
     * HTTP 方式：POST
     * API 路径：/api/message/updateMessageIsTop
     * 方法名：updateMessageIsTop
     * 方法返回类型：String
     */
    @ApiOperation(value = "留言信息置顶、取消置顶", notes = "留言信息置顶、取消置顶", produces = "application/json")
    @RequestMapping(value = "/api/message/updateMessageIsTop", method = RequestMethod.POST)
    public String updateMessageIsTop(@ApiParam(value = "主键ID", required = true) @RequestParam(value = "id", required = true) Integer id,
                                     @ApiParam(value = "是否置顶：1是，2否", required = true) @RequestParam(value = "isTop", required = true) Integer isTop) {
        logger.debug("Request RESTful API:updateMessageIsTop");
        logger.debug("id：" + id);
        logger.debug("isTop：" + isTop);

        UpdateWrapper<MessageEntity> updateWrapper = new UpdateWrapper();
        updateWrapper.eq("id", id);
        if (isTop.equals(1)) {
            updateWrapper.set("top_time", new Date());
        } else {
            updateWrapper.set("top_time", null);
        }
        boolean bol = messageService.update(updateWrapper);
        if (bol) {
            return new JsonResponseData(true, StatusDefineMessage.getMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS, "", "").toString();
        } else {
            return new JsonResponseData(false, StatusDefineMessage.getMessage(StatusDefine.FAILURE), StatusDefine.FAILURE, "", "").toString();
        }

    }
}
