package com.aorise.controller.banner;

import com.aorise.model.banner.BannerEntity;
import com.aorise.service.common.UploadService;
import com.aorise.utils.StatusDefine;
import com.aorise.utils.StatusDefineMessage;
import com.aorise.utils.define.ConstDefine;
import com.aorise.utils.json.JsonResponseData;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mysql.jdbc.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.aorise.service.banner.BannerService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* 轮播图 控制器
* @author cat
* @version 1.0
*/
@RestController
@Api(value="轮播图模块",tags = "轮播图模块")
public class BannerController {

    //日志打印器
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 轮播图服务接口
     */
    @Autowired
    BannerService bannerService;
    @Autowired
    UploadService uploadService;

    /**
     * 查询全部轮播图信息
     * HTTP 方式：GET
     * API 路径：/api/banner
     * 方法名：getAllBanner
     * 方法返回类型：String
     */
    @ApiOperation(value="查询全部轮播图信息", notes="查询全部轮播图信息",produces = "application/json")
    @RequestMapping(value="/api/banner", method= RequestMethod.GET)
    public String getAllBanner(@ApiParam(value = "类型：1活动，2景点", required = false) @RequestParam(value = "type",required = false)Integer type) {
        logger.debug("Request RESTful API:getAllBanner");
        logger.debug("type：" + type);

        List<BannerEntity> bannerEntities;

            //装载查询条件
            QueryWrapper<BannerEntity> entity = new QueryWrapper<>();
            if (type!=null && type>0) {
                entity.eq("type", type);
            }
            entity.eq("is_delete",ConstDefine.IS_NOT_DELETE);
            bannerEntities = bannerService.list(entity);
            return new JsonResponseData(true, StatusDefineMessage.getMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS, "", bannerEntities).toString();

    }

    /**
     * 根据ID查询轮播图信息
     * HTTP 方式：GET
     * API 路径：/api/banner/id/{id}
     * 方法名：getBannerById
     * 方法返回类型：String
     */
    @ApiOperation(value="根据ID查询轮播图信息", notes="根据ID查询轮播图信息",produces = "application/json")
    @RequestMapping(value="/api/banner/id/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getBannerById(@ApiParam(value = "主键ID", required = true) @PathVariable(value = "id", required = true) Integer id) {
        logger.debug("Request RESTful API:getBannerById");
        logger.debug("id：" + id);
        BannerEntity bannerEntity = null;

            QueryWrapper<BannerEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", id);
            queryWrapper.eq("is_delete", ConstDefine.IS_NOT_DELETE);
            bannerEntity = bannerService.getOne(queryWrapper);

        return new JsonResponseData(true, StatusDefineMessage.getMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS, "", bannerEntity).toString();
    }

    /**
     * 新增轮播图信息
     * HTTP 方式：POST
     * API 路径：/api/banner/addBanner
     * 方法名：addBanner
     * 方法返回类型：String
     */
    @ApiOperation(value="新增轮播图信息", notes="新增轮播图信息",produces = "application/json")
    @RequestMapping(value="/api/banner/addBanner", method= RequestMethod.POST)
    public String addBanner(@RequestBody @Validated BannerEntity bannerEntity) {
        logger.debug("Request RESTful API:addBanner");
        logger.debug("banner：" + bannerEntity);

            boolean bol = bannerService.save(bannerEntity);
            if (bol) {
                return new JsonResponseData(true, StatusDefineMessage.getMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS, "", bannerEntity.getId()).toString();
            }else {
                return new JsonResponseData(false, StatusDefineMessage.getMessage(StatusDefine.FAILURE), StatusDefine.FAILURE, "","").toString();
            }

    }

    /**
     * 修改轮播图信息
     * HTTP 方式：POST
     * API 路径：/api/banner/updateBanner
     * 方法名：updateBanner
     * 方法返回类型：String
     */
    @ApiOperation(value="修改轮播图信息", notes="修改轮播图信息",produces = "application/json")
    @RequestMapping(value="/api/banner/updateBanner", method= RequestMethod.POST)
    public String updateBanner(@RequestBody @Validated BannerEntity bannerEntity, HttpServletRequest request) {
        logger.debug("Request RESTful API:updateBanner");
        logger.debug("banner：" + bannerEntity);


            BannerEntity b =bannerService.getById(bannerEntity.getId());

            bannerEntity.setIsDelete(ConstDefine.IS_NOT_DELETE);
            boolean bol = bannerService.updateById(bannerEntity);
            if (bol) {
                if(!b.getPic().equals(bannerEntity.getPic())) {
                    //删除图片文件
                    uploadService.deletefile(b.getPic(),request);
                }
                return new JsonResponseData(true, StatusDefineMessage.getMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS, "","").toString();
            }else {
                return new JsonResponseData(false, StatusDefineMessage.getMessage(StatusDefine.FAILURE), StatusDefine.FAILURE, "","").toString();
            }

    }

    /**
     * 删除轮播图信息
     * HTTP 方式：POST
     * API 路径：/api/banner/id/{id}
     * 方法名：deleteBanner
     * 方法返回类型：String
     */
    @ApiOperation(value="删除轮播图信息", notes="删除轮播图信息",produces = "application/json")
    @RequestMapping(value="/api/banner/id/{id}", method= RequestMethod.POST)
    public String deleteBanner(@ApiParam(value = "主键ID", required = true) @PathVariable(value = "id", required = true) Integer id,
                               HttpServletRequest request) {
        logger.debug("Request RESTful API:deleteBanner");
        logger.debug("id：" + id);


            BannerEntity bannerEntity =new BannerEntity();
            bannerEntity.setIsDelete(ConstDefine.IS_DELETE);
            bannerEntity.setId(id);
            boolean bool = bannerService.updateById(bannerEntity);


            if (bool) {
                //删除图片文件
                BannerEntity b =bannerService.getById(id);
                uploadService.deletefile(b.getPic(),request);
                return new JsonResponseData(true, StatusDefineMessage.getMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS, "","").toString();
            }else {
                return new JsonResponseData(false, StatusDefineMessage.getMessage(StatusDefine.FAILURE), StatusDefine.FAILURE, "","").toString();
            }

    }

}
