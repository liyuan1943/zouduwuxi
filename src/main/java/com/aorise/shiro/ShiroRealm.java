package com.aorise.shiro;

import com.aorise.model.system.SysPermissionModel;
import com.aorise.model.system.SysRoleModel;
import com.aorise.model.system.SysUserModel;
import com.aorise.service.system.SystemService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author cat
 * @Description  实现AuthorizingRealm抽象类  身份认证doGetAuthenticationInfo  和授权doGetAuthorizationInfo
 * @date  Created on 2018/7/9.
 * @modified By:
 */
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private SystemService systemService;


    //授权，每一次访问都会授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SysUserModel use = (SysUserModel) principals.getPrimaryPrincipal();//doGetAuthenticationInfo方法注入什么对象就转什么对象
        //查询角色权限
        SysUserModel user = systemService.findByUsername(use.getUserName());
        if(null == user){
            return null;
        }
        SimpleAuthorizationInfo AuthorizationInfo = new SimpleAuthorizationInfo();
//        for (SysRoleModel role : user.getSysRoles()) {
//            AuthorizationInfo.addRole(role.getName());  // 添加角色
//            for (SysPermissionModel per : user.getSysPermissions()) {
//                AuthorizationInfo.addStringPermission(per.getCode());  // 添加具体权限
//            }
//        }
        return AuthorizationInfo;
    }

    //登录认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        String username = (String) token.getPrincipal(); // 获取用户登录账号
        SysUserModel userInfo = systemService.findByUsername(username);
        if(null == userInfo){
            throw new UnknownAccountException("用户不存在");
        }
        // 1). principal: 认证的实体信息. 可以是 username,也可以是数据表对应的用户的实体类对象,如果登录时需要返回用户的所有信息，则赋值用户对象
        Object principal = userInfo;
        // 2). credentials: 密码.
        Object credentials = userInfo.getPassWord();
        // 3). realmName: 当前 realm 对象的唯一名字. 调用父类的 getName() 方法
        String realmName = super.getName();
        // 4). credentialsSalt: 盐值. 类型是ByteSource
        ByteSource credentialsSalt = ByteSource.Util.bytes(userInfo.getUserName());//将用户名作为盐
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal, credentials, credentialsSalt, realmName);
        return info;
    }
}
