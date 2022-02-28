package com.aorise.utils.fileutil;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author liyuan
 * @Description
 * @date  Created in 2018/4/14
 * @modified By:
 */
public interface FileUtil {

    String upload(MultipartFile multipartFiles, String folder);

    Boolean deleteFile(String key);

}
