package com.aorise.service.common.impl;

import com.aorise.exceptions.ServiceException;
import com.aorise.service.common.UploadService;
import com.aorise.utils.Upload;
import com.aorise.utils.Utils;
import com.aorise.utils.setting.FileuploadSetting;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

/**
 * @author cat
 * @Description
 * @date  Created in 2018-09-27 10:00
 * @modified By:
 */
@Service
public class UploadServiceImpl implements UploadService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FileuploadSetting fileuploadSetting;

    /**
     * 上传文件
     * @param file
     * @param request
     * @return
     */
    @Override
    public String uploadImg(MultipartFile file,HttpServletRequest request) throws Exception {
        //String contentType = file.getContentType();
        String fileName = file.getOriginalFilename();
        fileName =(int)((Math.random()*9+1)*10000) +System.currentTimeMillis()+ fileName;
        String perfileName = fileName.substring(0,fileName.lastIndexOf("."));
        //md5加密
        String md5PerfileName = Utils.getMd5DigestAsHex(perfileName);
        String lastfileName = fileName.substring(fileName.lastIndexOf("."));
        String newFileName = md5PerfileName+lastfileName;
        String filePath = request.getSession().getServletContext().getRealPath(fileuploadSetting.getSavepath());
        //String filePath = fileuploadSetting.getImgURL();

        //上传文件
        String result = Upload.uploadFile(file.getBytes(), filePath, newFileName);
        result =fileuploadSetting.getFileURL()+fileuploadSetting.getSavepath()+result;
        return result;
    }

    /**
     * 删除文件
     * @param fileName
     * @param request
     * @return
     */
    @Override
    public boolean deleteImg(String fileName,HttpServletRequest request) throws Exception {
        if (StringUtils.isBlank(fileName)) {
            throw new ServiceException("文件名为空");
        }
        if (fileName.split("\\.")[0].length()!=32) {
            return true;
        }

        String filePath = request.getSession().getServletContext().getRealPath(fileuploadSetting.getSavepath());
        File file = new File(filePath+fileName);
        if (!file.exists()) {
            logger.info("delete file fail because file is not exists !");
            return true;
        } else {
            boolean deleteFile = Upload.deleteFile(filePath+fileName);
            return deleteFile;
        }
    }

    /**
     * 批量删除图片
     * @param fileNames
     * @param request
     * @return
     */
    @Override
    public boolean deleteImgList(String fileNames,HttpServletRequest request) throws Exception {
        String filePath = request.getSession().getServletContext().getRealPath(fileuploadSetting.getSavepath());

        String[] fileNameArr = fileNames.split(",");
        boolean deleteFile = true;
        for (String fileName : fileNameArr) {

            if (StringUtils.isNotBlank(fileName)) {
                if (fileName.split("\\.")[0].length() == 32) {
                    File file = new File(filePath + fileName);
                    if (!file.exists()) {
                        logger.info("delete file fail because file is not exists !");
                    } else {
                        deleteFile = Upload.deleteFile(filePath + fileName);
                        if (!deleteFile) {
                            logger.error("delete file fail: " + filePath + fileName);
                        }
                    }
                }
            }
        }
        return deleteFile;
    }

    /**
     * 后台通过图片路径删除文件
     * @param url
     * @param request
     * @return
     */
    @Override
    public boolean deletefile(String url,HttpServletRequest request) {
        try {

            String fileName = Utils.getFileNameFromUrl(url);

            boolean deleteFile = true;
            if (StringUtils.isNotBlank(fileName)) {

                if (fileName.split("\\.")[0].length() == 32) {

                    String filePath = request.getSession().getServletContext().getRealPath(fileuploadSetting.getSavepath());
                    File file = new File(filePath + fileName);
                    if (file.exists()) {
                        deleteFile = Upload.deleteFile(filePath + fileName);
                    }
                }
            }
            return deleteFile;
        } catch (Exception e) {
            logger.error("后台删除文件失败!url:" + url + "; err:" +e.getMessage());
            return false;
        }
    }

}
