package com.aorise.service.medal.impl;

import com.aorise.exceptions.ServiceException;
import com.aorise.mapper.medal.MedalMapper;
import com.aorise.mapper.scenic.ScenicAchievementMapper;
import com.aorise.model.medal.MedalEntity;
import com.aorise.model.scenic.ScenicAchievementEntity;
import com.aorise.service.common.UploadService;
import com.aorise.service.medal.MedalService;
import com.aorise.service.scenic.ScenicService;
import com.aorise.utils.define.ConstDefine;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 勋章 ServiceImpl层
 *
 * @author cat
 * @version 1.0
 */
@Service
@Transactional(rollbackFor = {Exception.class})
public class MedalServiceImpl extends ServiceImpl<MedalMapper, MedalEntity> implements MedalService {

    @Autowired
    UploadService uploadService;
    @Autowired
    ScenicService scenicService;
    @Autowired
    ScenicAchievementMapper scenicAchievementMapper;

    /**
     * 新增勋章
     *
     * @param medalEntity 勋章
     * @return int 影响行数
     * @author cat
     * @date Created in 2018/9/20 9:27
     * @modified By:
     */
    @Override
    public int addMedal(MedalEntity medalEntity) {
        boolean bol = this.save(medalEntity);
        if (bol) {
            return 1;
        } else {
            return -1;
        }
    }

    /**
     * 修改勋章
     *
     * @param medalEntity 勋章
     * @param request     request
     * @return int 影响行数
     * @author cat
     * @date Created in 2018/9/20 9:27
     * @modified By:
     */
    @Override
    public int updateMedal(MedalEntity medalEntity, HttpServletRequest request) {
        medalEntity.setIsDelete(ConstDefine.IS_NOT_DELETE);
        boolean bol = this.updateById(medalEntity);
        if (bol) {
            return 1;
        } else {
            return -1;
        }
    }

    /**
     * 根据会员ID查询获得的勋章信息
     *
     * @param memberId 会员ID
     * @return List<MedalEntity> 勋章信息
     * @author cat
     * @date Created in 2018/9/20 9:27
     * @modified By:
     */
    @Override
    public List<MedalEntity> getMedalByMemberId(String memberId) {
        //查询所有勋章
        QueryWrapper<MedalEntity> medalEntityQueryWrapper = new QueryWrapper<>();
        medalEntityQueryWrapper.orderByAsc("sort");
        List<MedalEntity> medalEntities = this.list(medalEntityQueryWrapper);

        //查询该会员景点成就
        QueryWrapper<ScenicAchievementEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("member_id", memberId);
        List<ScenicAchievementEntity> scenicAchievementEntities = scenicAchievementMapper.selectList(queryWrapper);
        for (MedalEntity medalEntity : medalEntities) {
            //获取勋章关联景点集合
            String[] scenicIdArr = medalEntity.getScenicId().split(",");
            List<String> scenicIdList = Arrays.asList(scenicIdArr);
            //获取会员完成成就的景点集合
            List<String> finishScenicIdList = scenicAchievementEntities.stream().map(e -> e.getScenicId().toString()).collect(Collectors.toList());
            boolean isHave = finishScenicIdList.containsAll(scenicIdList);
            if (isHave) {
                medalEntity.setIsGet(ConstDefine.IS_YES);
            } else {
                medalEntity.setIsGet(ConstDefine.IS_NO);
            }
        }
        return medalEntities;
    }
}
