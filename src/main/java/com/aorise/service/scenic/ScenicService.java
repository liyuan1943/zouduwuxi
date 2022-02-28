package com.aorise.service.scenic;

import com.aorise.model.activity.ActivityEntity;
import com.aorise.model.scenic.ScenicEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
* 景点 Service层
* @author cat
* @version 1.0
*/
public interface ScenicService extends IService<ScenicEntity> {

    /**
     * 根据ID查询景点信息
     * @param id 景点ID
     * @return ScenicEntity
     * @author cat
     * @date  Created in 2018/9/20 9:27
     * @modified By:
     */
    ScenicEntity getScenicById(Integer id);

    /**
     * 新增景点
     *
     * @param scenicEntity 景点
     * @return int 影响行数
     * @author cat
     * @date Created in 2018/9/20 9:27
     * @modified By:
     */
    int addScenic(ScenicEntity scenicEntity);

    /**
     * 修改景点
     *
     * @param scenicEntity 景点
     * @param request request
     * @return int 影响行数
     * @author cat
     * @date Created in 2018/9/20 9:27
     * @modified By:
     */
    int updateScenic(ScenicEntity scenicEntity, HttpServletRequest request);

    /**
     * 删除景点
     *
     * @param id 景点ID
     * @param request request
     * @return int 影响行数
     * @author cat
     * @date Created in 2018/9/20 9:27
     * @modified By:
     */
    int deleteScenic(int id, HttpServletRequest request);
}
