package com.aorise.model.wechat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author cat
 * @Description 多条图文消息model
 * @date  2018-04-12  10:00
 * @modified  By:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class NewsMessageRespModel extends BaseMessageRespModel {

    public enum NewsMessageRespModelMapper implements RowMapper<NewsMessageRespModel> {
        INSTANCE;

        @Override
        public NewsMessageRespModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            NewsMessageRespModel newsMessageRespModel = new NewsMessageRespModel();
            newsMessageRespModel.setArticleCount(rs.getInt("articleCount"));

            return newsMessageRespModel;
        }
    }

    private int articleCount;// 图文消息个数，限制为10条以内
    private List<ArticleRespModel> articleRespModelList;// 多条图文消息信息，默认第一个item为大图


}
