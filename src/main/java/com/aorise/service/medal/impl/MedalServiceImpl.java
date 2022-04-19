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
        if (medalEntity.getIsYear() == ConstDefine.IS_YES) {
            //判断不能添加多个相同年份的勋章
            QueryWrapper<MedalEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("year", medalEntity.getYear());
            queryWrapper.eq("is_year", ConstDefine.IS_YES);
            queryWrapper.eq("is_delete", ConstDefine.IS_NOT_DELETE);
            MedalEntity medal = this.getOne(queryWrapper);
            if (medal != null) {
                throw new ServiceException(medalEntity.getYear() + "年度勋章已存在");
            }
        }
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
        if (medalEntity.getIsYear() == ConstDefine.IS_YES) {
            //判断不能添加多个相同年份的勋章
            QueryWrapper<MedalEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("year", medalEntity.getYear());
            queryWrapper.eq("is_year", ConstDefine.IS_YES);
            queryWrapper.eq("is_delete", ConstDefine.IS_NOT_DELETE);
            MedalEntity medal = this.getOne(queryWrapper);
            if (medal != null) {
                if (!medal.getId().equals(medalEntity.getId())) {
                    throw new ServiceException(medalEntity.getYear() + "年度勋章已存在");
                }
            }
        }
        MedalEntity b = this.getById(medalEntity.getId());
        medalEntity.setIsDelete(ConstDefine.IS_NOT_DELETE);
        boolean bol = this.updateById(medalEntity);
        if (bol) {
            if (!b.getPic().equals(medalEntity.getPic())) {
                //删除图片文件
                uploadService.deletefile(b.getPic(), request);
            }
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
        for(MedalEntity medalEntity : medalEntities){
            if(medalEntity.getIsYear()==ConstDefine.IS_NO) {
                //如果不是年度勋章
                medalEntity.setIsGet(ConstDefine.IS_NO);
                for (ScenicAchievementEntity scenicAchievementEntity : scenicAchievementEntities) {
                    //判断勋章关联的景点是否已完成成就
                    if(medalEntity.getScenicId()!= null && medalEntity.getScenicId().equals(scenicAchievementEntity.getScenicId().toString())){
                        medalEntity.setIsGet(ConstDefine.IS_YES);
                        break;
                    }
                }
            }else {
                //如果是年度勋章
                //获取勋章关联景点集合
                String [] scenicIdArr = medalEntity.getScenicId().split(",");
                List<String> scenicIdList = Arrays.asList(scenicIdArr);
                //获取会员完成成就的景点集合
                List<String> finishScenicIdList = scenicAchievementEntities.stream().map(e->e.getScenicId().toString()).collect(Collectors.toList());
                boolean isHave = finishScenicIdList.containsAll(scenicIdList);
                if(isHave){
                    medalEntity.setIsGet(ConstDefine.IS_YES);
                }else {
                    medalEntity.setIsGet(ConstDefine.IS_NO);
                }
            }
        }
        return medalEntities;
    }
}
