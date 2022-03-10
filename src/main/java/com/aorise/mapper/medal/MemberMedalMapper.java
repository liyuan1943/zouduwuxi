package com.aorise.mapper.medal;

import com.aorise.model.medal.MemberMedalEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
* 会员勋章关联表 DAO接口
* @author cat
* @version 1.0
*/
@Mapper
@Component(value = "memberMedalMapper")
public interface MemberMedalMapper extends BaseMapper<MemberMedalEntity> {

}
