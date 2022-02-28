package com.aorise.model.wechat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author cat
 * @Description 文本消息model
 * @date  2018-04-12  10:00
 * @modified  By:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TextMessageRespModel extends BaseMessageRespModel {

    public enum TextMessageRespModelMapper implements RowMapper<TextMessageRespModel> {
        INSTANCE;
        @Override
        public TextMessageRespModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            TextMessageRespModel textMessageRespModel = new TextMessageRespModel();
            textMessageRespModel.setContent(rs.getString("content"));

            return textMessageRespModel;
        }
    }

    private String content;// 回复的消息内容

}
