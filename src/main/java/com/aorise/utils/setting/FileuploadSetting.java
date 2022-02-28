package com.aorise.utils.setting;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by YYP on 2018/4/13.
 */
@ConfigurationProperties(prefix = "upload")
public class FileuploadSetting {
    private String savepath;
    private String fileURL;
    private String imgURL;

    public String getSavepath() {
        return savepath;
    }

    public void setSavepath(String savepath) {
        this.savepath = savepath;
    }

    public String getFileURL() {
        return fileURL;
    }

    public void setFileURL(String fileURL) {
        this.fileURL = fileURL;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }
}
