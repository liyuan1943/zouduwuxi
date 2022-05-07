package com.aorise.utils;

/**
 * Created by tanshaoxing on 2017/11/20.
 * 常量接口定义API接口返回值
 */

public interface StatusDefine {

    /**
     * 用户相关 100X
     * 角色相关 110X
     * 权限相关 120X
     * 验证相关 200X
     * 商品相关 300X
     * 活动相关 400X
     *
     * 系统相关 500X
     * 文件相关 700X
     *自定义异常相关 900X
     * */
    //操作成功
    int SUCCESS = 0;
    int FAILURE = -1;

    //参数不匹配
    int PARAM_NUL=30005;

    //系统状态
    //系统错误
    int SYS_ERROR = 5001;
    //网络请求失败
    int NET_ERROR = 5002;
    //数据库访问失败
    int DB_ERROR = 5003;
    //业务逻辑层出错
    int SERVICE_ERROR=5004;


    //空指针异常
    int NULL_POINTER_ERROR=5006;

    //文件服务器出错
    int FILE_SERVICE_ERROR=7001;
    //缓存redis服务器出错
    int REDIS_SERVICE_ERROR = 7002;
    //读取Excel文件失败
    int READ_EXCEL_ERROR=7003;
    //文件不存在
    int FILE_NOT_FOUND = 7004;
    //上传文件过大
    int FILE_SIZE_MAX_ERROR=7005;

    //数据合法化验证
    //数据格式不正确
    int DATA_FORMAT_ERROR=2001;
    //文件格式不正确
    int FILE_FORMAT_ERROR=2002;
    //传入参数为空
    int ISNULLPARAM = 2003;
    //传入参数类型异常
    int PARAM_TYPE_ERROR=2004;

    //用户相关
    //用户不存在d
    int U_INEXISTENCE = 1001;
    //输入的账号密码有误，请重新输入
    int U_PWD_FAILED = 1002;
    //用户未激活
    int U_UNACTIVE = 1003;
    //用户未登录
    int U_UNLOAD =1004;
    //用户已经存在
    int U_EXIST_USER = 1005;
    //登录超时
    int U_LOGIN_OUTTIME =1006;
    //非法访问
    int U_ILLEGALITY = 1007;
    //新密码和确认新密码输入不一致
    int U_PWD_TWO_INCONFORMITY=1008;
    //原密码输入错误
    int U_PWD_OLD_FAILED=1009;
    //无权登录APP
    int U_CANT_LOGINAPP=1010;
    //JWTtoken过期。重新生成
    int U_NEW_TOKEN=1111;
    //新密码不能与原密码一致
    int U_PWD_OLD_INCONFORMITY=1112;



    //角色已存在
    int ROLE_EXIST=1101;

    //用户未授权
    int U_NO_TOKEN = 1201;
    //当前账号无权限，请联系管理员
    int PERMISSIONDENIED = 1202;
    //有权限
    int HAVEPERMISSIOND=1203;

    //人员删除失败
    int PERSON_DEL_ERROR=1301;

    int GRID_DEL_ERROR = 1401;

    //商品已被占用

    //活动
    int TRAVEL_EXIST=4001;

    //组织已被占用
    int ORGAN_EXIST=9001;
    //组织超过限制层级
    int ORGAN_MAX=9002;
    //选择的设备信息为空
    int CAMERA_NULL=9003;
    //只能撤回待审批的申请
    int APPLICATION_DELETE=9004;
    //设备编码已存在
    int CAMERA_NUMBER_EXIST=9005;
    //该申请已被审批
    int APPROVE_EXIST=9006;
}
