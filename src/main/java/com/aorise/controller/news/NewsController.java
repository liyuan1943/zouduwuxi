package com.aorise.controller.news;

import com.aorise.model.news.NewsEntity;
import com.aorise.service.common.UploadService;
import com.aorise.service.news.NewsService;
import com.aorise.utils.StatusDefine;
import com.aorise.utils.StatusDefineMessage;
import com.aorise.utils.define.ConstDefine;
import com.aorise.utils.json.JsonResponseData;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
* 公告 控制器
* @author cat
* @version 1.0
*/
@RestController
@Api(value="公告模块",tags = "公告模块")
public class NewsController {

    //日志打印器
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 公告服务接口
     */
    @Autowired
    NewsService newsService;
    @Autowired
    UploadService uploadService;

    /**
     * 分页查询公告信息
     * HTTP 方式：GET
     * API 路径：/api/news/pageIndex/{pageIndex}/pageNum/{pageNum}
     * 方法名：getNewsByPage
     * 方法返回类型：String
     */
    @ApiOperation(value="分页查询公告信息", notes="分页查询公告信息",produces = "application/json")
    @RequestMapping(value="/api/news/pageIndex/{pageIndex}/pageNum/{pageNum}", method= RequestMethod.GET)
    public String getNewsByPage(@ApiParam(value = "公告标题", required = false) @RequestParam(value = "title",required = false)String title,
                                @ApiParam(value = "页索引", required = true) @PathVariable(value = "pageIndex", required = true) Integer pageIndex,
                                @ApiParam(value = "页大小", required = true) @PathVariable(value = "pageNum", required = true) Integer pageNum) {
        logger.debug("Request RESTful API:getNewsByPage");
        logger.debug("title：" + title);
        logger.debug("pageIndex：" + pageIndex);
        logger.debug("pageNum：" + pageNum);

        Page<NewsEntity> page = new Page<>(pageIndex, pageNum);
        try {
            //装载查询条件
            QueryWrapper<NewsEntity> entity = new QueryWrapper<>();
            if (StringUtils.isNotBlank(title)) {
                entity.like("title", title);
            }

            entity.eq("is_delete", ConstDefine.IS_NOT_DELETE);
            entity.orderByDesc("create_date");
            page = newsService.page(page, entity);

        } catch (Exception e) {

            return new JsonResponseData(false, StatusDefineMessage.getMessage(StatusDefine.FAILURE), StatusDefine.FAILURE, "", "").toString();
        }
        return new JsonResponseData(true, StatusDefineMessage.getMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS, "", page).toString();
    }

    /**
     * 根据ID查询公告信息
     * HTTP 方式：GET
     * API 路径：/api/news/id/{id}
     * 方法名：getNewsById
     * 方法返回类型：String
     */
    @ApiOperation(value="根据ID查询公告信息", notes="根据ID查询公告信息",produces = "application/json")
    @RequestMapping(value="/api/news/id/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getNewsById(@ApiParam(value = "主键ID", required = true) @PathVariable(value = "id", required = true) Integer id) {
        logger.debug("Request RESTful API:getNewsById");
        logger.debug("id：" + id);
        NewsEntity newsEntity = null;
        try {
            QueryWrapper<NewsEntity> entity = new QueryWrapper<>();
            entity.eq("id", id);
            entity.eq("is_delete", ConstDefine.IS_NOT_DELETE);
            newsEntity = newsService.getOne(entity);
        }catch (Exception e){

            return new JsonResponseData(false, StatusDefineMessage.getMessage(StatusDefine.FAILURE), StatusDefine.FAILURE, "","").toString();
        }
        return new JsonResponseData(true, StatusDefineMessage.getMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS, "", newsEntity).toString();
    }

    /**
     * 新增公告信息
     * HTTP 方式：POST
     * API 路径：/api/news/addNews
     * 方法名：addNews
     * 方法返回类型：String
     */
    @ApiOperation(value="新增公告信息", notes="新增公告信息",produces = "application/json")
    @RequestMapping(value="/api/news/addNews", method= RequestMethod.POST)
    public String addNews(@RequestBody @Validated NewsEntity newsEntity) {
        logger.debug("Request RESTful API:addNews");
        logger.debug("news：" + newsEntity);

        try {
            boolean bol = newsService.save(newsEntity);
            if (bol) {
                return new JsonResponseData(true, StatusDefineMessage.getMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS, "", newsEntity.getId()).toString();
            }else {
                return new JsonResponseData(false, StatusDefineMessage.getMessage(StatusDefine.FAILURE), StatusDefine.FAILURE, "","").toString();
            }
        } catch (Exception e) {

            return new JsonResponseData(false, StatusDefineMessage.getMessage(StatusDefine.FAILURE), StatusDefine.FAILURE, "","").toString();
        }
    }

    /**
     * 修改公告信息
     * HTTP 方式：POST
     * API 路径：/api/news/updateNews
     * 方法名：updateNews
     * 方法返回类型：String
     */
    @ApiOperation(value="修改公告信息", notes="修改公告信息",produces = "application/json")
    @RequestMapping(value="/api/news/updateNews", method= RequestMethod.POST)
    public String updateNews(@RequestBody @Validated NewsEntity newsEntity, HttpServletRequest request) {
        logger.debug("Request RESTful API:updateNews");
        logger.debug("news：" + newsEntity);

        try {
            NewsEntity n = newsService.getById(newsEntity.getId());
            boolean bol = newsService.updateById(newsEntity);
            if (bol) {
                //删除图片文件
                if(!n.getPic().equals(newsEntity.getPic())) {
                    uploadService.deletefile(n.getPic(),request);
                }
                return new JsonResponseData(true, StatusDefineMessage.getMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS, "","").toString();
            }else {
                return new JsonResponseData(false, StatusDefineMessage.getMessage(StatusDefine.FAILURE), StatusDefine.FAILURE, "","").toString();
            }
        } catch (Exception e) {

            return new JsonResponseData(false, StatusDefineMessage.getMessage(StatusDefine.FAILURE), StatusDefine.FAILURE, "","").toString();
        }
    }

    /**
     * 删除公告信息
     * HTTP 方式：POST
     * API 路径：/api/news/id/{id}
     * 方法名：deleteNews
     * 方法返回类型：String
     */
    @ApiOperation(value="删除公告信息", notes="删除公告信息",produces = "application/json")
    @RequestMapping(value="/api/news/id/{id}", method= RequestMethod.POST)
    public String deleteNews(@ApiParam(value = "主键ID", required = true) @PathVariable(value = "id", required = true) Integer id,
                             HttpServletRequest request) {
        logger.debug("Request RESTful API:deleteNews");
        logger.debug("id：" + id);

        try {
            NewsEntity newsEntity =new NewsEntity();
            newsEntity.setIsDelete(ConstDefine.IS_DELETE);
            newsEntity.setId(id);
            boolean bool = newsService.updateById(newsEntity);
            if (bool) {
                //删除图片文件
                NewsEntity n = newsService.getById(id);
                uploadService.deletefile(n.getPic(),request);
                return new JsonResponseData(true, StatusDefineMessage.getMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS, "","").toString();
            }else {
                return new JsonResponseData(false, StatusDefineMessage.getMessage(StatusDefine.FAILURE), StatusDefine.FAILURE, "","").toString();
            }
        } catch (Exception e) {

            return new JsonResponseData(false, StatusDefineMessage.getMessage(StatusDefine.FAILURE), StatusDefine.FAILURE, "","").toString();
        }
    }


}
