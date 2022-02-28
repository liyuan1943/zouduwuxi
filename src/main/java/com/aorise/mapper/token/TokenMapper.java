package com.aorise.mapper.token;

import com.aorise.model.token.TokenEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.Map;


/**
* @author cat
* @Description token信息mybatis接口类
* @date   2018-04-08  12:04
* @modified By:
*/
@Mapper
@Component(value = "tokenMapper")
public interface TokenMapper {

    /**
     * @author cat
     * @Description 根据id查询token信息
     * @params: map 条件Map
     * @date  2018-04-08  14:03
     * @return  token信息
     * @modified By:
     */
    TokenEntity getTokenById(Map<String, Object> map) throws DataAccessException;

    /**
     * @author cat
     * @Description 修改token信息
     * @params: map 条件Map
     * @date  2018-04-17  14:03
     * @return  Integer 影响行数
     * @modified By:
     */
    Integer updateToken(Map<String, Object> map) throws DataAccessException;

}