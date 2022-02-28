package com.aorise.model.wechat;

import lombok.Data;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author cat
 * @Description 消息基类（公众帐号 -> 普通用户）
 * @date   2018-04-12  10:00
 * @modified By:
 */
@Data
public class BaseMessageRespModel {

    public enum BaseMessageRespModelMapper implements RowMapper<BaseMessageRespModel> {
        INSTANCE;
        @Override
        public BaseMessageRespModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            BaseMessageRespModel baseMessageRespModel = new BaseMessageRespModel();
            baseMessageRespModel.setToUserName(rs.getString("toUserName"));
            baseMessageRespModel.setFromUserName(rs.getString("fromUserName"));
            baseMessageRespModel.setCreateTime(rs.getLong("createTime"));
            baseMessageRespModel.setMsgType(rs.getString("msgType"));
            //baseMessageRespModel.setFuncFlag(rs.getInt("funcFlag"));

            return baseMessageRespModel;
        }
    }


    private String toUserName;// 接收方帐号（收到的OpenID）
    private String fromUserName;// 开发者微信号
    private long createTime;// 消息创建时间 （整型）
    private String msgType;// 消息类型（text/music/news）
    //private int funcFlag;// 位0x0001被标志时，星标刚收到的消息


}
