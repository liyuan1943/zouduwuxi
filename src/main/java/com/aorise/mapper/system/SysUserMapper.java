package com.aorise.mapper.system;

import com.aorise.model.system.SysUserModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * @author cat
 * @Description   用户相关
 * @date  Created in 2018/9/20 9:10
 * @modified By:
 */

@Mapper
@Component(value = "SysUserMapper")
public interface SysUserMapper {

    /**
     * @author cat
     * @Description    根据用户名查询用户
     * @param username 用户名
     * @date  Created in 2018/9/20 9:27
     * @return userModel
     * @modified By:
     */
    SysUserModel findByUsername(@Param("username")String username)throws DataAccessException;

    /**
     * @author cat
     * @Description    根据id查询用户
     * @param id 用户id
     * @date  Created in 2018/9/20 9:27
     * @return userModel
     * @modified By:
     */
    SysUserModel findObject(@Param("id") int id)throws DataAccessException;

    /**
     * @author cat
     * @Description    修改用户
     * @param model 用户对象
     * @date  Created in 2018/9/20 9:27
     * @return userModel
     * @modified By:
     */
    int editeObject(SysUserModel model)throws DataAccessException;


}
