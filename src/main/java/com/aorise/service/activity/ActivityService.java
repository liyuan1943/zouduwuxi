package com.aorise.service.activity;

import com.aorise.model.activity.ActivityEntity;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
* 活动 Service层
* @author cat
* @version 1.0
*/
public interface ActivityService extends IService<ActivityEntity> {

    /**
     * 分页查询活动信息
     *
     * @param map  查询条件
     * @param page 分页
     * @return Page 活动信息集合
     * @author yulu
     * @date 2020-08-14
     */
    Page<ActivityEntity> getActivityByPage(Map<String, Object> map, Page<ActivityEntity> page);

    /**
     * 根据ID查询活动信息
     * @param id 活动ID
     * @return ActivityEntity
     * @author cat
     * @date  Created in 2018/9/20 9:27
     * @modified By:
     */
    ActivityEntity getActivityById(Integer id);

    /**
     * 新增活动
     *
     * @param activityEntity 活动
     * @return int 影响行数
     * @author cat
     * @date Created in 2018/9/20 9:27
     * @modified By:
     */
    int addActivity(ActivityEntity activityEntity);

    /**
     * 修改活动
     *
     * @param activityEntity 活动
     * @param request request
     * @return int 影响行数
     * @author cat
     * @date Created in 2018/9/20 9:27
     * @modified By:
     */
    int updateActivity(ActivityEntity activityEntity, HttpServletRequest request);

    /**
     * 删除活动
     *
     * @param id 活动ID
     * @param request request
     * @return int 影响行数
     * @author cat
     * @date Created in 2018/9/20 9:27
     * @modified By:
     */
    int deleteActivity(int id, HttpServletRequest request);
}
