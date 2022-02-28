package com.aorise.service.system;


import com.aorise.model.system.SysPermissionModel;
import com.aorise.model.system.SysRoleModel;
import com.aorise.model.system.SysUserModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author cat
 * @Description  系统管理相关
 * @date  Created in 2018/9/21 13:47
 * @modified By:
 */
@Service
public interface SystemService {

    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return userModel
     * @author cat
     * @date  Created in 2018/9/20 9:27
     * @modified By:
     */
    SysUserModel findByUsername(String username);

    /**
     * 根据用户id查询用户
     * @param id 用户id
     * @return userModel
     * @author cat
     * @date  Created in 2018/9/20 9:27
     * @modified By:
     */
    SysUserModel findObject(int id);

    /**
     * 修改用户
     * @param model 用户对象
     * @return userModel
     * @author cat
     * @date  Created in 2018/9/20 9:27
     * @modified By:
     */
    int editeObject(SysUserModel model);




}
