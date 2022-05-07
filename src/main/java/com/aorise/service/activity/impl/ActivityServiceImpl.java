package com.aorise.service.activity.impl;

import com.aorise.mapper.activity.ActivityMapper;
import com.aorise.mapper.activity.ActivityScenicMapper;
import com.aorise.mapper.scenic.ScenicAchievementMapper;
import com.aorise.model.activity.ActivityEntity;
import com.aorise.model.activity.ActivityScenicEntity;
import com.aorise.model.scenic.ScenicAchievementEntity;
import com.aorise.model.scenic.ScenicEntity;
import com.aorise.service.activity.ActivityService;
import com.aorise.service.common.UploadService;
import com.aorise.service.scenic.ScenicService;
import com.aorise.utils.Utils;
import com.aorise.utils.define.ConstDefine;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 活动 ServiceImpl层
 *
 * @author cat
 * @version 1.0
 */
@Service
@Transactional(rollbackFor = {Exception.class})
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, ActivityEntity> implements ActivityService {

    @Autowired
    ActivityScenicMapper activityScenicMapper;

    @Autowired
    ScenicService scenicService;

    @Autowired
    UploadService uploadService;

    @Autowired
    ScenicAchievementMapper scenicAchievementMapper;

    /**
     * 分页查询活动信息
     *
     * @param map  查询条件
     * @param page 分页
     * @return Page 活动信息集合
     * @author yulu
     * @date 2020-08-14
     */
    @Override
    public Page<ActivityEntity> getActivityByPage(Map<String, Object> map, Page<ActivityEntity> page) {
        String name = map.get("name") == null ? null : map.get("name").toString();
        String isOpenning = map.get("isOpenning") == null ? "0" : map.get("isOpenning").toString();

        //装载查询条件
        QueryWrapper<ActivityEntity> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(name)) {
            queryWrapper.like("name", name);
        }
        String newDay = Utils.dateToStr(new Date(), "yyyy-MM-dd");
        if (isOpenning.equals(String.valueOf(ConstDefine.ACTIVITY_WAIT))) {
            queryWrapper.gt("begin_date", newDay);
        }
        if (isOpenning.equals(String.valueOf(ConstDefine.ACTIVITY_OPENNING))) {
            queryWrapper.le("begin_date", newDay);
            queryWrapper.ge("expiration_date", newDay);
        }
        if (isOpenning.equals(String.valueOf(ConstDefine.ACTIVITY_END))) {
            queryWrapper.lt("expiration_date", newDay);
        }
        queryWrapper.eq("is_delete", ConstDefine.IS_NOT_DELETE);
        queryWrapper.orderByDesc("expiration_date");
        queryWrapper.orderByDesc("begin_date");
        page = this.page(page, queryWrapper);

        //查询活动是否开放
        List<ActivityEntity> entities = page.getRecords();
        if (entities != null) {
            for (ActivityEntity activityEntity : entities) {
                Date dateBegin = Utils.strToDate(activityEntity.getBeginDate(), "yyyy-MM-dd");
                Date dateExpiration = Utils.strToDate(activityEntity.getExpirationDate(), "yyyy-MM-dd");
                Date dateNow = Utils.strToDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()), "yyyy-MM-dd");
                if (dateBegin.compareTo(dateNow) > 0) {
                    activityEntity.setIsOpenning(ConstDefine.ACTIVITY_WAIT);
                } else if (dateBegin.compareTo(dateNow) <= 0 && dateExpiration.compareTo(dateNow) >= 0) {
                    activityEntity.setIsOpenning(ConstDefine.ACTIVITY_OPENNING);
                } else if (dateExpiration.compareTo(dateNow) < 0) {
                    activityEntity.setIsOpenning(ConstDefine.ACTIVITY_END);
                } else {
                    activityEntity.setIsOpenning(0);
                }
            }
        }
        return page;
    }

    /**
     * 根据ID查询活动信息
     *
     * @param id 活动ID
     * @return ActivityEntity
     * @author cat
     * @date Created in 2018/9/20 9:27
     * @modified By:
     */
    @Override
    public ActivityEntity getActivityById(Integer id) {
        QueryWrapper<ActivityEntity> entity = new QueryWrapper<>();
        entity.eq("id", id);
        entity.eq("is_delete", ConstDefine.IS_NOT_DELETE);
        ActivityEntity activityEntity = this.getOne(entity);
        if (activityEntity != null) {
            //查询活动关联的景点
            QueryWrapper<ActivityScenicEntity> activityScenicEntityQueryWrapper = new QueryWrapper<>();
            activityScenicEntityQueryWrapper.eq("activity_id", id);
            List<ActivityScenicEntity> activityScenicEntities = activityScenicMapper.selectList(activityScenicEntityQueryWrapper);
            if (activityScenicEntities.size() > 0) {
                List<ScenicEntity> scenicEntities = new ArrayList<>();
                for (ActivityScenicEntity activityScenicEntity : activityScenicEntities) {
                    ScenicEntity scenicEntity = scenicService.getScenicById(activityScenicEntity.getScenicId());
                    if (scenicEntity != null) {
                        //查询景点打卡人次
                        QueryWrapper<ScenicAchievementEntity> queryWrapper = new QueryWrapper<>();
                        queryWrapper.eq("scenic_id", scenicEntity.getId());
                        int count = scenicAchievementMapper.selectCount(queryWrapper);
                        scenicEntity.setFinishNum(count);
                    }
                    scenicEntities.add(scenicEntity);
                }
                activityEntity.setScenicEntities(scenicEntities);
            }
            //查询活动是否开放
            Date dateBegin = Utils.strToDate(activityEntity.getBeginDate(), "yyyy-MM-dd");
            Date dateExpiration = Utils.strToDate(activityEntity.getExpirationDate(), "yyyy-MM-dd");
            Date dateNow = Utils.strToDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()), "yyyy-MM-dd");
            if (dateBegin.compareTo(dateNow) > 0) {
                activityEntity.setIsOpenning(ConstDefine.ACTIVITY_WAIT);
            } else if (dateBegin.compareTo(dateNow) <= 0 && dateExpiration.compareTo(dateNow) >= 0) {
                activityEntity.setIsOpenning(ConstDefine.ACTIVITY_OPENNING);
            } else if (dateExpiration.compareTo(dateNow) < 0) {
                activityEntity.setIsOpenning(ConstDefine.ACTIVITY_END);
            } else {
                activityEntity.setIsOpenning(0);
            }
        }
        return activityEntity;
    }

    /**
     * 新增活动
     *
     * @param activityEntity 活动
     * @return int 影响行数
     * @author cat
     * @date Created in 2018/9/20 9:27
     * @modified By:
     */
    @Override
    public int addActivity(ActivityEntity activityEntity) {
        boolean bol = this.save(activityEntity);
        if (bol) {
            //新增活动景点关联
            String[] sIds = activityEntity.getScenicIds().split(",");
            for (String sId : sIds) {
                ActivityScenicEntity activityScenicEntity = new ActivityScenicEntity();
                activityScenicEntity.setActivityId(activityEntity.getId());
                activityScenicEntity.setScenicId(Integer.parseInt(sId));
                activityScenicMapper.insert(activityScenicEntity);
            }
            return 1;
        } else {
            return -1;
        }
    }

    /**
     * 修改活动
     *
     * @param activityEntity 活动
     * @param request        request
     * @return int 影响行数
     * @author cat
     * @date Created in 2018/9/20 9:27
     * @modified By:
     */
    @Override
    public int updateActivity(ActivityEntity activityEntity, HttpServletRequest request) {
        ActivityEntity oldActivity = this.getById(activityEntity.getId());
        boolean bol = this.updateById(activityEntity);
        if (bol) {
            //查询旧关联景点
            QueryWrapper<ActivityScenicEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("activity_id", activityEntity.getId());
            List<ActivityScenicEntity> activityScenicEntities = activityScenicMapper.selectList(queryWrapper);
            if (activityScenicEntities.size() > 0) {
                //删除旧关联景点
                for (ActivityScenicEntity activityScenicEntity : activityScenicEntities) {
                    activityScenicMapper.deleteById(activityScenicEntity.getId());
                }
            }

            //新增关联景点
            String[] sIds = activityEntity.getScenicIds().split(",");
            for (String sId : sIds) {
                ActivityScenicEntity activityScenicEntity = new ActivityScenicEntity();
                activityScenicEntity.setActivityId(activityEntity.getId());
                activityScenicEntity.setScenicId(Integer.parseInt(sId));
                activityScenicMapper.insert(activityScenicEntity);
            }
            return 1;
        } else {
            return -1;
        }
    }

    /**
     * 删除活动
     *
     * @param id      活动ID
     * @param request request
     * @return int 影响行数
     * @author cat
     * @date Created in 2018/9/20 9:27
     * @modified By:
     */
    @Override
    public int deleteActivity(int id, HttpServletRequest request) {
        ActivityEntity activityEntity = new ActivityEntity();
        activityEntity.setId(id);
        activityEntity.setIsDelete(ConstDefine.IS_DELETE);
        boolean bol = this.updateById(activityEntity);
        if (bol) {
            //删除旧图片文件
            ActivityEntity oldActivity = this.getById(id);
            uploadService.deletefile(oldActivity.getBgi(), request);

            //查询旧关联景点
            QueryWrapper<ActivityScenicEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("activity_id", activityEntity.getId());
            List<ActivityScenicEntity> activityScenicEntities = activityScenicMapper.selectList(queryWrapper);
            if (activityScenicEntities.size() > 0) {
                //删除旧关联景点
                for (ActivityScenicEntity activityScenicEntity : activityScenicEntities) {
                    activityScenicMapper.deleteById(activityScenicEntity.getId());
                }
            }
            return 1;
        } else {
            return -1;
        }
    }
}
