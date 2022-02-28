package com.aorise.service.member.impl;

import com.aorise.mapper.member.MemberMapper;
import com.aorise.model.member.MemberEntity;
import com.aorise.service.member.MemberService;
import com.aorise.utils.Utils;
import com.aorise.utils.define.ConstDefine;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author cat
 * @Description  会员管理业务
 * @date  Created in 2018/9/21 13:48
 * @modified By:
 */
@Transactional(rollbackFor = {Exception.class})
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, MemberEntity> implements MemberService {

    @Autowired
    private MemberMapper memberMapper;

    /**
     * 根据会员ID查询会员信息
     * @param id 会员ID
     * @return memberModel
     * @author cat
     * @date  Created in 2018/9/20 9:27
     * @modified By:
     */
    @Override
    public MemberEntity getMemberInfoByMemberId(Integer id) {
        MemberEntity memberEntity = this.getById(id);
        return memberEntity;
    }

    /**
     * 根据会员ID删除会员
     * @param memberId 会员ID
     * @return int 影响行数
     * @author cat
     * @date  Created in 2018/9/20 9:27
     * @modified By:
     */
    @Override
    public int deleteMemberInfoByMemberId(Integer memberId) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId(memberId);
        memberEntity.setIsDelete(ConstDefine.IS_DELETE);
        boolean bol =this.updateById(memberEntity);
        if (bol){
            return 1;
        }else {
            return -1;
        }
    }


}
