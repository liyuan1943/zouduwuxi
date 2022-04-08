package com.aorise.controller.common;

import com.aorise.exceptions.FileServiceException;
import com.aorise.exceptions.ServiceException;
import com.aorise.service.common.UploadService;
import com.aorise.utils.StatusDefine;
import com.aorise.utils.StatusDefineMessage;
import com.aorise.utils.json.JsonResponseData;
import com.aorise.utils.setting.FileuploadSetting;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @author cat
 * @Description
 * @date  Created in 2017-08-25 15:18
 * @modified By:
 */
@RestController
@Configuration
@EnableConfigurationProperties(FileuploadSetting.class)
@Api(value="上传图片",tags = "上传图片")
public class UploadController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UploadService uploadService;


    /**
     * 上传图片
     * HTTP 方式：POST
     * API 路径：/api/upload
     * 方法名：UploadImg
     * 方法返回类型：String
     * 方法异常：Exception
     */
    @ApiOperation ( value = "上传图片", httpMethod = "POST", response = String.class, notes = "上传图片,所有文件由此上传" )
    @RequestMapping( value = "/api/upload/UploadImg", method = RequestMethod.POST )
    public String UploadImg(@ApiParam ( value = "文件", required = true ) @RequestParam( value = "file" ) MultipartFile file,
                            HttpServletRequest request) {

            String result = uploadService.uploadImg(file,request);

            return new JsonResponseData(true, StatusDefineMessage.getMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS, "", result).toString();

    }

    /**
     * 删除图片
     * HTTP 方式：POST
     * API 路径：/api/upload
     * 方法名：deleteImg
     * 方法返回类型：String
     * 方法异常：Exception
     */
    @ApiOperation ( value = "删除图片", httpMethod = "POST", response = String.class, notes = "删除图片" )
    @RequestMapping(value="/api/upload/deleteImg", method= RequestMethod.POST)
    public String deleteImg(@ApiParam ( value = "文件名", required = true ) @RequestParam( value = "fileName" ) String fileName,
                             HttpServletRequest request) {

        logger.debug(fileName);


            boolean deleteFile = uploadService.deleteImg(fileName,request);
            //删除方法
            if (deleteFile) {
                return new JsonResponseData(true, StatusDefineMessage.getMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS, "", null).toString();
            } else {
                return new JsonResponseData(false, StatusDefineMessage.getMessage(StatusDefine.FAILURE), StatusDefine.FAILURE, "删除失败", null).toString();
            }

    }

    /**
     * 批量删除图片
     * HTTP 方式：POST
     * API 路径：/api/upload
     * 方法名：deleteImgList
     * 方法返回类型：String
     * 方法异常：Exception
     */
    @ApiOperation ( value = "批量删除图片", httpMethod = "POST", response = String.class, notes = "批量删除图片" )
    @RequestMapping(value="/api/upload/deleteImgList", method= RequestMethod.POST)
    public String deleteImgList(@ApiParam ( value = "文件名集合(逗号分隔)", required = true ) @RequestParam( value = "fileNames" ) String fileNames,
                            HttpServletRequest request) {

        logger.debug(fileNames);

            if (StringUtils.isBlank(fileNames)) {
                return new JsonResponseData(false, StatusDefineMessage.getMessage(StatusDefine.FAILURE), StatusDefine.FAILURE, "文件名为空", null).toString();
            }

            boolean deleteFile = uploadService.deleteImgList(fileNames,request);

            if (deleteFile) {
                return new JsonResponseData(true, StatusDefineMessage.getMessage(StatusDefine.SUCCESS), StatusDefine.SUCCESS, "", null).toString();
            } else {
                return new JsonResponseData(false, StatusDefineMessage.getMessage(StatusDefine.FAILURE), StatusDefine.FAILURE, "删除失败", null).toString();
            }

    }

}
