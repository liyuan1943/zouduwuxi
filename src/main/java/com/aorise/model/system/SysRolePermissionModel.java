package com.aorise.model.system;

/**
 * @author cat
 * @Description  角色权限实体类
 * @date  Created in 2018-09-17 11:28
 * @modified By:
 */
public class SysRolePermissionModel {
    private Integer id;
    private Integer permissionId;
    private Integer roleId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
