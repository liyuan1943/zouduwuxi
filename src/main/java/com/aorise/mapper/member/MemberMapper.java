package com.aorise.mapper.member;

import com.aorise.model.member.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @author cat
 * @Description   会员相关
 * @date  Created in 2018/9/20 9:10
 * @modified By:
 */

@Mapper
@Component(value = "memberMapper")
public interface MemberMapper extends BaseMapper<MemberEntity> {


}
