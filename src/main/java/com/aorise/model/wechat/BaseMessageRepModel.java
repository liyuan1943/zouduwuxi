package com.aorise.model.wechat;

import lombok.Data;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author cat
 * @Description 消息基类（普通用户 -> 公众帐号）
 * @date   2018-04-12  10:00
 * @modified By:
 */
@Data
public class BaseMessageRepModel {

    public enum BaseMessageRepModelMapper implements RowMapper<BaseMessageRepModel> {
        INSTANCE;
        @Override
        public BaseMessageRepModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            BaseMessageRepModel baseMessageRepModel = new BaseMessageRepModel();
            baseMessageRepModel.setToUserName(rs.getString("toUserName"));
            baseMessageRepModel.setFromUserName(rs.getString("fromUserName"));
            baseMessageRepModel.setCreateTime(rs.getLong("createTime"));
            baseMessageRepModel.setMsgType(rs.getString("msgType"));
            baseMessageRepModel.setMsgId(rs.getLong("msgId"));

            return baseMessageRepModel;
        }
    }

    private String toUserName;// 开发者微信号
    private String fromUserName;// 发送方帐号（一个OpenID）
    private long createTime;// 消息创建时间 （整型）
    private String msgType;// 消息类型（text/image/location/link）
    private long msgId;// 消息id，64位整型

}
