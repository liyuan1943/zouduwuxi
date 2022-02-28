package com.aorise.service.news.impl;

import com.aorise.mapper.news.NewsMapper;
import com.aorise.model.news.NewsEntity;
import com.aorise.service.news.NewsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* 公告 ServiceImpl层
* @author cat
* @version 1.0
*/
@Service
@Transactional(rollbackFor = {Exception.class})
public class NewsServiceImpl extends ServiceImpl<NewsMapper, NewsEntity> implements NewsService {

}
