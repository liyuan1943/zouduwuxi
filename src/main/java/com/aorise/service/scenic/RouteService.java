package com.aorise.service.scenic;

import com.aorise.model.scenic.RouteEntity;
import com.aorise.model.scenic.ScenicEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* 景点 Service层
* @author cat
* @version 1.0
*/
public interface RouteService extends IService<RouteEntity> {

    /**
     * 查询路线列表
     *
     * @return List<RouteEntity>
     * @param  scenicId
     * @author cat
     * @date 2019-07-10
     */
    List<RouteEntity> getRouteList(Integer scenicId);

    /**
     * 新增路线
     *
     * @param routeEntity 路线
     * @return int 影响行数
     * @author cat
     * @date Created in 2018/9/20 9:27
     * @modified By:
     */
    int addRoute(RouteEntity routeEntity);

    /**
     * 修改路线
     *
     * @param routeEntity 路线
     * @return int 影响行数
     * @author cat
     * @date Created in 2018/9/20 9:27
     */
    int updateRoute(RouteEntity routeEntity);

    /**
     * 删除路线
     *
     * @param id      路线ID
     * @param request request
     * @return int 影响行数
     * @author cat
     * @date Created in 2018/9/20 9:27
     */
    int deleteRoute(int id, HttpServletRequest request);
}
