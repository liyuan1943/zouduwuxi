package com.aorise;

import com.aorise.model.system.SysUserRoleModel;
//import com.aorise.utils.PushMessage;
//import com.aorise.utils.swift.JCloudsNova;
import org.joda.time.DateTime;
import org.joda.time.Months;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@RunWith ( SpringRunner.class )
@SpringBootTest
public class KeyProjectManageApplicationTests {
    @Resource
    private DataSource dataSource;


    @Test
    public void contextLoads() {
        SysUserRoleModel model = new SysUserRoleModel();
        model.setUserId(1);
        model.setRoleId(2);
        try {
//            pushMessage.PushMessage("52","推送测试");
        } catch (Exception e) {

        }
    }

    @Test
    public void Test() {

//        jCloudsNova.listContainers();
    }

}
