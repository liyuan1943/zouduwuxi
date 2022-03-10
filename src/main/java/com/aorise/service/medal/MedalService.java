package com.aorise.service.medal;

import com.aorise.model.banner.BannerEntity;
import com.aorise.model.medal.MedalEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* 勋章 Service层
* @author cat
* @version 1.0
*/
public interface MedalService extends IService<MedalEntity> {

    /**
     * 新增勋章
     *
     * @param medalEntity 勋章
     * @return int 影响行数
     * @author cat
     * @date Created in 2018/9/20 9:27
     * @modified By:
     */
    int addMedal(MedalEntity medalEntity);

    /**
     * 修改勋章
     *
     * @param medalEntity 勋章
     * @param request request
     * @return int 影响行数
     * @author cat
     * @date Created in 2018/9/20 9:27
     * @modified By:
     */
    int updateMedal(MedalEntity medalEntity, HttpServletRequest request);

    /**
     * 根据会员ID查询获得的勋章信息
     *
     * @param memberId 会员ID
     * @return List<MedalEntity> 勋章信息
     * @author cat
     * @date Created in 2018/9/20 9:27
     * @modified By:
     */
    List<MedalEntity> getMedalByMemberId(String memberId);
}
