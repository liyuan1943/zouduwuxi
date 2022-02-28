package com.aorise.mapper.news;

import com.aorise.model.news.NewsEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
* 公告 DAO接口
* @author cat
* @version 1.0
*/
@Mapper
@Component(value = "newsMapper")
public interface NewsMapper extends BaseMapper<NewsEntity> {

}
