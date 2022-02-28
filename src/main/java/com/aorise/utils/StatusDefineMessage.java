package com.aorise.utils;

/**
 * Created by tanshaoxing on 2017/11/20.
 * API常量值信息获取
 */
public class StatusDefineMessage {

    //获取常量信息
    public static String getMessage(int s) {
        switch (s) {
            case StatusDefine.SUCCESS:
                return "操作成功";
            case StatusDefine.FAILURE:
                return "操作失败";
            case StatusDefine.U_INEXISTENCE:
                return "用户不存在";
            case StatusDefine.U_PWD_FAILED:
                return "输入的账号密码有误，请重新输入";
            case StatusDefine.U_UNACTIVE:
                return "用户未激活";
            case StatusDefine.U_NO_TOKEN:
                return "用户未授权";
            case StatusDefine.SYS_ERROR:
                return "系统错误";
            case StatusDefine.NET_ERROR:
                return "网络请求失败";
            case StatusDefine.DB_ERROR:
                return "数据库访问失败";
            case StatusDefine.FILE_SERVICE_ERROR:
                return "文件服务器出错";
            case StatusDefine.READ_EXCEL_ERROR:
                return "读取EXCEL文件失败";
            case StatusDefine.FILE_FORMAT_ERROR:
                return "文件格式错误";
            case StatusDefine.PERMISSIONDENIED:
                return "当前账号无权限，请联系管理员";
            case StatusDefine.U_UNLOAD:
                return "用户未登录";
            case StatusDefine.U_LOGIN_OUTTIME:
                return "登录超时,请重新登录";
            case StatusDefine.HAVEPERMISSIOND:
                return "有权限";
            case StatusDefine.REDIS_SERVICE_ERROR:
                return "缓存服务器出错";
            case StatusDefine.ISNULLPARAM:
                return "传入参数为空";
            case StatusDefine.U_EXIST_USER:
                return "用户已经存在";
            case StatusDefine.SERVICE_ERROR:
                return "业务逻辑层出错";
            case StatusDefine.DATA_FORMAT_ERROR:
                return "入参数据格式不正确";
            case StatusDefine.PARAM_TYPE_ERROR:
                return "请求参数类型不匹配";
            case StatusDefine.NULL_POINTER_ERROR:
                return "空指针异常";
            case StatusDefine.U_ILLEGALITY:
                return "非法访问，请携带正确的令牌";
            case StatusDefine.U_PWD_TWO_INCONFORMITY:
                return "新密码和确认新密码输入不一致";
            case StatusDefine.U_PWD_OLD_FAILED:
                return "原密码输入错误";
            case StatusDefine.ROLE_EXIST:
                return "角色已存在";
            case StatusDefine.PERSON_DEL_ERROR:
                return "人员删除失败";
            case StatusDefine.GRID_DEL_ERROR:
                return "网格已被使用,无法删除";
            case StatusDefine.U_CANT_LOGINAPP:
                return "单位账户无权登录APP";
            case StatusDefine.ORGAN_EXIST:
                return "组织已被占用";
            case StatusDefine.ORGAN_MAX:
                return "组织超过限制层级";
            case StatusDefine.CAMERA_NULL:
                return "选择的设备信息为空";
            case StatusDefine.APPLICATION_DELETE:
                return "只能撤回待审批的申请";
            case StatusDefine.CAMERA_NUMBER_EXIST:
                return "设备编码已存在";
            case StatusDefine.APPROVE_EXIST:
                return "该申请已被审批";
            case StatusDefine.U_PWD_OLD_INCONFORMITY:
                return "新密码不能与原密码一致";
            case StatusDefine.TRAVEL_EXIST:
                return "活动名称已存在";
            default:
                return "";
        }
    }
}
