package com.aorise.service.system.impl;

import com.aorise.mapper.system.*;
import com.aorise.model.system.*;
import com.aorise.service.system.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author cat
 * @Description 用户管理业务
 * @date Created in 2018/9/21 13:48
 * @modified  By:
 */
@Transactional
@Service
public class SystemServiceImpl implements SystemService {
    @Autowired
    private SysUserMapper sysUserMapper;



    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return userModel
     * @author cat
     * @date  Created in 2018/9/20 9:27
     * @modified By:
     */
    @Override
    public SysUserModel findByUsername(String username) {
        SysUserModel user = sysUserMapper.findByUsername(username);
        return user;
    }

    /**
     * 根据用户id查询用户
     * @param id 用户id
     * @return userModel
     * @author cat
     * @date  Created in 2018/9/20 9:27
     * @modified By:
     */
    @Override
    public SysUserModel findObject(int id) {
        return sysUserMapper.findObject(id);
    }


    /**
     * 修改用户
     * @param model 用户对象
     * @return userModel
     * @author cat
     * @date  Created in 2018/9/20 9:27
     * @modified By:
     */
    @Override
    public int editeObject(SysUserModel model) {

        return sysUserMapper.editeObject(model);
    }


}
