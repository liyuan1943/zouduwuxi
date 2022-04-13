package com.aorise.controller.statistic.vo;


import lombok.Data;

/**
* 景点排行查询结果VO
* @author cat
* @version 1.0
*/
@Data
public class ScenicRankVo {

    /**
     * 会员ID
     */
    private Integer memberId;

    /**
     * 排名
     */
    private Integer no;

    /**
     * 会员头像
     */
    private String headPic;

    /**
     * 会员昵称
     */
    private String nickname;

    /**
     * 总打卡次数
     */
    private Integer checkSum;

    /**
     * 打卡景点数
     */
    private Integer scenicSum;


}
