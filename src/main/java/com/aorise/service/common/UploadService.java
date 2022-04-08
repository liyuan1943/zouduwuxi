package com.aorise.service.common;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author cat
 * @Description  上传文件接口类
 * @date  Created in 2018-09-27 09:59
 * @modified By:
 */
public interface UploadService {

    /**
     * 上传文件
     * @param file
     * @param request
     * @return
     */
    String uploadImg(MultipartFile file, HttpServletRequest request);

    /**
     * 删除文件
     * @param fileName
     * @param request
     * @return
     */
    boolean deleteImg(String fileName,HttpServletRequest request) ;

    /**
     * 批量删除图片
     * @param fileNames
     * @param request
     * @return
     */
    boolean deleteImgList(String fileNames,HttpServletRequest request) ;

    /**
     * 后台通过图片路径删除文件
     * @param url
     * @param request
     * @return
     */
    boolean deletefile(String url,HttpServletRequest request);
}
