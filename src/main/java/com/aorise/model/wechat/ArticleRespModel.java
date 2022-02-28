package com.aorise.model.wechat;

import lombok.Data;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author cat
 * @Description 图文model
 * @date   2018-04-12  10:00
 * @modified By:
 */
@Data
public class ArticleRespModel {

    public enum ArticleRespModelMapper implements RowMapper<ArticleRespModel> {
        INSTANCE;
        @Override
        public ArticleRespModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            ArticleRespModel articleRespModel = new ArticleRespModel();
            articleRespModel.setTitle(rs.getString("title"));
            articleRespModel.setDescription(rs.getString("description"));
            articleRespModel.setPicUrl(rs.getString("picUrl"));
            articleRespModel.setUrl(rs.getString("url"));

            return articleRespModel;
        }
    }

    private String title;// 图文消息名称
    private String description;// 图文消息描述
    private String picUrl;// 图片链接，支持JPG、PNG格式，较好的效果为大图640*320，小图80*80，限制图片链接的域名需要与开发者填写的基本资料中的Url一致
    private String url;// 点击图文消息跳转链接

}
