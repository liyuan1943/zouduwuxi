package com.aorise.mapper.system;

import com.aorise.model.system.SysUserModel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @author cat
 * @Description   用户相关
 * @date  Created in 2018/9/20 9:10
 * @modified By:
 */


@Mapper
@Component(value = "sysUserMapper")
public interface SysUserMapper extends BaseMapper<SysUserModel> {


}
