package com.aorise.utils;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aorise.exceptions.FileServiceException;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * @author cat
 * @Description  ExcelUtil工具类
 * @date  Created in 2017-08-16 16:41
 * @modified By:
 */

public class Upload {

    /**
     * @param   file  文件
     * @param   filePath  文件路径
     * @param   fileName  文件名称
     * @return 路径
     */
    public static String uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        FileOutputStream out = null;
        try {
            File targetFile = new File(filePath);
            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }
            out = new FileOutputStream(filePath + fileName);
            out.write(file);
            out.flush();
        }catch (Exception e){
            throw new FileServiceException("文件上传出错！");
        }finally {
            if(out != null) {
                out.close();
            }
        }
        return fileName;
    }
    /**
     * @param   fileName 文件名称
     * @return   成功或者失败
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
