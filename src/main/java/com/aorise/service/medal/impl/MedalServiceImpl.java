package com.aorise.service.medal.impl;

import com.aorise.exceptions.ServiceException;
import com.aorise.mapper.medal.MedalMapper;
import com.aorise.mapper.medal.MemberMedalMapper;
import com.aorise.model.medal.MedalEntity;
import com.aorise.model.medal.MemberMedalEntity;
import com.aorise.service.common.UploadService;
import com.aorise.service.medal.MedalService;
import com.aorise.utils.define.ConstDefine;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

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
    MemberMedalMapper memberMedalMapper;

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
        if (medalEntity.getScenicId() == 0) {
            //景点ID=0的是年度勋章，判断不能添加多个相同年份的勋章
            QueryWrapper<MedalEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("year", medalEntity.getYear());
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
        if (medalEntity.getScenicId() == 0) {
            //景点ID=0的是年度勋章，判断不能添加多个相同年份的勋章
            QueryWrapper<MedalEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("year", medalEntity.getYear());
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
        QueryWrapper<MemberMedalEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("member_id", memberId);
        List<MemberMedalEntity> memberMedalEntities = memberMedalMapper.selectList(queryWrapper);
        List<MedalEntity> medalEntities = new ArrayList<>();
        for(MemberMedalEntity memberMedalEntity : memberMedalEntities){
            //查询勋章信息
            MedalEntity medalEntity= this.getById(memberMedalEntity.getMedalId());
            medalEntities.add(medalEntity);
        }
        return medalEntities;
    }
}
