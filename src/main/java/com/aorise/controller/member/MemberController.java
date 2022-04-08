package com.aorise.controller.member;

import com.aorise.model.member.MemberEntity;
import com.aorise.service.member.MemberService;
import com.aorise.utils.StatusDefine;
import com.aorise.utils.StatusDefineMessage;
import com.aorise.utils.define.ConstDefine;
import com.aorise.utils.json.JsonResponseData;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author cat
 * @Description  会员管理接口
 * @date  Created in 2018/9/21 14:14
 * @modified By:
 */
@RestController
@Api(value="会员管理相关接口",tags ="会员管理相关接口" )
public class MemberController {

    @Autowired
    private MemberService memberService;

    /**
     * 日志打印器
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * 根据会员ID删除会员
     * HTTP 方式：POST
     * API 路径：/api/member/deleteMember
     * 方法名：deleteMemberInfoByMemberId
     * 方法返回类型：String
     */
    @ApiOperation(value = "根据会员ID删除会员", httpMethod = "POST", response = String.class, notes = "系统管理-会员管理-删除会员")
    @RequestMapping(value = "/api/member/deleteMember", method = RequestMethod.POST)
    public String deleteMember(@ApiParam(value = "会员ID", required = true) @RequestParam(value = "memberId", required = true) Integer memberId) {

            logger.debug("Request RESTful API:deleteMember");
            logger.info("memberId：" + memberId);

            //调用接口
            memberService.deleteMemberInfoByMemberId(memberId);
            return new JsonResponseData(true, StatusDefineMessage.getMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS,
                    "", null).toString();

    }

    /**
     * 会员详情-根据会员ID查询会员信息
     * HTTP 方式：GET
     * API 路径：/api/member/id/{id}
     * 方法名：insertObject
     * 方法返回类型：String
     */
    @ApiOperation(value = "根据会员ID查询会员信息", httpMethod = "GET", response = String.class, notes = "系统管理-会员管理-根据会员ID查询会员信息")
    @RequestMapping(value = "/api/member/id/{id}", method = RequestMethod.GET, produces = "application/json")
    public String getMemberById(@ApiParam(value = "会员ID", required = true) @PathVariable(value = "memberId", required = true) Integer id) {

            logger.debug("Request RESTful API:id");
            logger.info("id：" + id);

            //调用接口
            MemberEntity model = memberService.getMemberInfoByMemberId(id);
            return new JsonResponseData(true, StatusDefineMessage.getMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS,
                    "", model).toString();


    }

    /**
     * 分页查询会员列表
     * HTTP 方式：POST
     * API 路径：/api/member
     * 方法名：getmemberListByPage
     * 方法返回类型：String
     */
    @ApiOperation(value = "分页查询会员列表", httpMethod = "GET", response = String.class, notes = "系统管理-会员管理-会员列表展示")
    @RequestMapping(value = "/api/member/pageIndex/{pageIndex}/pageNum/{pageNum}", method = RequestMethod.GET, produces = "application/json")
    public String getmemberListByPage(@ApiParam(value = "会员编号", required = false) @RequestParam(value = "memberCode", required = false) String memberCode,
                                       @ApiParam(value = "手机号码", required = false) @RequestParam(value = "phone", required = false) String phone,
                                       @ApiParam(value = "会员昵称", required = false) @RequestParam(value = "nickname", required = false) String nickname,
                                       @ApiParam(value = "页索引", required = true) @PathVariable(value = "pageIndex") int pageIndex,
                                       @ApiParam(value = "页大小", required = true) @PathVariable(value = "pageNum") int pageNum) {

            logger.debug("Request RESTful API:getmemberListByPage");
            logger.debug("memberCode" + memberCode);
            logger.debug("phone" + phone);
            logger.debug("nickname" + nickname);
            logger.debug("pageIndex" + pageIndex);
            logger.debug("pageNum" + pageNum);


            Page<MemberEntity> page = new Page<>(pageIndex, pageNum);
            //装载查询条件
            QueryWrapper<MemberEntity> wrapper = new QueryWrapper<>();

                if (!org.apache.commons.lang.StringUtils.isBlank(memberCode)) {
                    wrapper.like("code", memberCode);
                }
                if (!org.apache.commons.lang.StringUtils.isBlank(phone)) {
                    wrapper.like("phone_num", phone);
                }
                if (!StringUtils.isBlank(nickname)) {
                    wrapper.like("nickname", nickname);
                }
                wrapper.eq("is_delete", ConstDefine.IS_NOT_DELETE);
                wrapper.orderByDesc("create_date");
                page = memberService.page(page, wrapper);

            return new JsonResponseData(true, StatusDefineMessage.getMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS, "", page).toString();

    }


}
