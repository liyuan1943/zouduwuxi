package com.aorise.service.system.impl;

import com.aorise.mapper.system.QuestionMapper;
import com.aorise.model.system.QuestionEntity;
import com.aorise.service.system.QuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* 常见问题 ServiceImpl层
* @author cat
* @version 1.0
*/
@Service
@Transactional(rollbackFor = {Exception.class})
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, QuestionEntity> implements QuestionService {

}
