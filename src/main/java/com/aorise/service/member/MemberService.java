package com.aorise.service.member;


import com.aorise.model.member.MemberEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

/**
 * @author cat
 * @Description  会员管理service
 * @date  Created in 2018/9/21 13:47
 * @modified By:
 */
@Service
public interface MemberService extends IService<MemberEntity> {

    /**
     * 根据会员ID查询会员信息
     * @param id 会员ID
     * @return memberModel
     * @author cat
     * @date  Created in 2018/9/20 9:27
     * @modified By:
     */
    MemberEntity getMemberInfoByMemberId(Integer id);

    /**
     * 根据会员ID删除会员
     * @param memberId 会员ID
     * @return int 影响行数
     * @author cat
     * @date  Created in 2018/9/20 9:27
     * @modified By:
     */
    int deleteMemberInfoByMemberId(Integer memberId);


}
