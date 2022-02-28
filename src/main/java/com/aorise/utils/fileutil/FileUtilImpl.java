package com.aorise.utils.fileutil;

import com.aorise.config.FileuploadSetting;
import com.aorise.exceptions.FileServiceException;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.UUID;

/**
 * @author liyuan
 * @Description 文件上传
 * @date  Created in 2018/10/15
 * @modified By:
 */
@Service
public class FileUtilImpl implements FileUtil{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private FileuploadSetting fileuploadSetting;

    /**
     * @author liyuan
     * @Description
     * @param
     * @date  2018/10/15 10:01
     * @return
     * @modified By:
     */
    @Override
    public String upload(MultipartFile multipartFiles, String folder){
        String prefix = multipartFiles.getOriginalFilename().substring(multipartFiles.getOriginalFilename().lastIndexOf(".") + 1);
        if(prefix.equals("")|| StringUtils.isBlank(prefix)){
            prefix = multipartFiles.getName().substring(multipartFiles.getName().lastIndexOf(".") + 1);
        }
        try {
            String key = UUID.randomUUID().toString().replace("-", "")+"."+prefix;
            /**本机测试的时候 */
            String filePath = request.getSession().getServletContext().getRealPath("files/");
            /**部署到服务器的时候 */
//            String filePath = fileuploadSetting.getImgURL();
            //String filePath="C:\\Users\\Administrator\\Desktop\\imgInput\\";
            SaveFileFromInputStream(multipartFiles.getInputStream(),filePath+folder,key);
            return folder+key;
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new FileServiceException("文件服务器出错");
        } catch (FileServiceException e) {
            logger.error(e.getMessage());
            throw new FileServiceException("文件服务器出错");
        }
    }

    /**
     * @author liyuan
     * @Description
     * @param
     * @date  2018/10/15 10:01
     * @return
     * @modified By:
     */
    public void SaveFileFromInputStream(InputStream stream,String filePath, String filename) throws IOException {
        File file = new File(filePath);
        if(!file.exists()){
            file.mkdirs();
            file.createNewFile();
        }
        /* FileOutputStream fs=new FileOutputStream(filePath + filename);*/
        float quality=1f;
        if(stream.available()>=1024*1024*8){
            quality=0.2f;
        }else if(stream.available()<1024*1024*8&&stream.available()>=1024*1024*5){
            quality=0.25f;
        }else if(stream.available()<1024*1024*5&&stream.available()>=1024*1024*2){
            quality=0.3f;
        }
        Thumbnails.of(stream).scale(1f).outputQuality(quality)
                .outputFormat("jpg").toFile(filePath + filename.split("\\.")[0]);
       /* byte [] buffer  = new byte [ 1024 * 1024 ];
        int  bytesum  = 0 ;
        int  byteread  = 0 ;
        while((byteread = stream.read(buffer)) != - 1 ){
            bytesum += byteread;
            fs.write(buffer,0 ,byteread);
            fs.flush();
        }*/
       /* byte[] in_b = ByteStreams.toByteArray(stream);
        compressPic(in_b);
        fs.write(in_b);
        fs.close();*/
        stream.close();
    }

    /**
     * @author liyuan
     * @Description
     * @param
     * @date  2018/10/15 10:01
     * @return
     * @modified By:
     */
    @Override
    public Boolean deleteFile(String key) {
        String filePath = request.getSession().getServletContext().getRealPath("");
        try {
            File file = new File(filePath+key);
            if(!file.exists()){
                return false;
            }
            file.delete();
        } catch (FileServiceException e) {
            logger.error(e.getMessage());
            throw new FileServiceException("文件服务器出错");
        }
        return true;
    }
}
