package com.aorise.utils;
import com.aorise.utils.validation.DataValidation;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Months;
import org.joda.time.format.DateTimeFormat;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.joda.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 系统工具类
 * Create by tanshaoxing on 2017-07-19.
 */
public class Utils {
    //======================================== 基础工具 ========================================

    /**
     *<br> 字符串转日期
     *<br> 方法名： StrToDate
     * @param str 要转换的字符串
     * @param formatStr 转换格式
     * @return String 日期
     * @author yulu
     * @date 2019/9/10 11:19
     * @version 1.0
     */
    public static Date strToDate(String str,String formatStr) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {

        }
        return date;
    }

    /**
     *<br> 日期转字符串
     *<br> 方法名： StrToDate
     * @param date 要转换的日期
     * @param formatStr 转换格式
     * @return String 字符串
     * @author yulu
     * @date 2019/9/10 11:19
     * @version 1.0
     */
    public static String dateToStr(Date date,String formatStr) {

        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        String str = format.format(date);
        return str;
    }

    /**
     * MD5转码
     */
    public static String getMd5DigestAsHex(String input) {
        return DigestUtils.md5DigestAsHex(input.getBytes());
    }


    /**
     * Escape " for shell command.
     */
    public static String escapeShell(String s) {
        return s.replace("\"", "\\\"");
    }


    public static Date getCurrDateBefore(int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        return now.getTime();
    }

    //删除字符末尾零
    public static String delLastZero(String str) {
        if (StringUtils.isBlank(str)) return null;
        String tempStr = str;
        if (str.length() - 1 == str.lastIndexOf("0")) {
            tempStr = str.substring(0, str.length() - 1);
            tempStr = delLastZero(tempStr);
        }
        return tempStr;
    }

    /**
     * 产生4位随机数(0000-9999)
     * create by zhgp
     * @return 4位随机数
     */
    public static String getFourRandom(){
        Random random = new Random();
        String fourRandom = random.nextInt(10000) + "";
        int randLength = fourRandom.length();
        if(randLength<4){
            for(int i=1; i<=4-randLength; i++)
                fourRandom = "0" + fourRandom  ;
        }
        return fourRandom;
    }



   //查询快递名称
    public static String GetCourier(String code){
        switch (code) {
            case "SF": return "顺丰速运";
            case "HTKY": return "百世快递";
            case "ZTO": return "中通快递";
            case "STO": return "申通快递";
            case "YD": return "韵达速递";
            case "YZPY": return "邮政快递包裹";
            case "EMS": return "EMS";
            case "HHTT": return "天天快递";
            case "JD": return "京东";
            case "QFKD": return "全峰快递";
            case "GTO": return "国通快递";
            case "UC": return "优速快递";
            case "DBL": return "德邦";
            case "FAST": return "快捷快递";
            case "ZJS": return "宅急送";
            case "YTO": return "圆通速递";
            default:
                return "";

        }

    }
    //去除时间末尾0
    public static String DelTimeO(String time){
        if(!StringUtils.isBlank(time)) {
            time = time.substring(0, time.length() - 2);
        }
        return time;
    }

    /**
     * 获取当前时间
     * @return 返回当前时间字符串
     */
    public static String getCurrentTime(String format){
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(new Date()).toString();
    }
    /**
     * 根据数据库返回的0和1返回是和否
     * 方法名：CheckStringByInt
     * 方法返回类型：返回是活否
     */
    public static String CheckStringByInt(Integer num){
        if(num==0){
            return "否";
        }else if(num==1){
            return "是";
        }
        return "";
    }



    /**
     * 获取年龄段
     * @param ageGroup 所选年龄段
     * @return
     */
    public String getAgeStr(String ageGroup){
        StringBuilder paramSql=new StringBuilder();
        if (!StringUtils.isBlank(ageGroup)) {
            switch (Integer.parseInt(ageGroup)) {
                case 1:
                    paramSql.append(" and age !=0 and a.age<14  ");
                    break;
                case 2:
                    paramSql.append(" and a.age> 13 and a.age< 16  ");
                    break;
                case 3:
                    paramSql.append(" and a.age> 15 and a.age< 18 ");
                    break;
                case 4:
                    paramSql.append(" and a.age> 17 and a.age< 25 ");
                    break;
                case 5:
                    paramSql.append(" and a.age> 24 and a.age< 35 ");
                    break;
                case 6:
                    paramSql.append(" and a.age> 34 and a.age< 45 ");
                    break;
                case 7:
                    paramSql.append(" and a.age> 44 and a.age< 55 ");
                    break;
                case 8:
                    paramSql.append(" and a.age> 54 and a.age< 70 ");
                    break;
                case 9:
                    paramSql.append(" and a.age > 70 ");
                    break;
                default:
                    break;
            }
        }
        return paramSql.toString();
    }
    /**
     * 判断两个字符串是否相同
     * @param str1
     * @param str2
     * @return
     */
    public static boolean isSame(String str1, String str2) {
        if (str1 != null) {
            if (str2 != null) {
                String[] str1s = str1.split(",");
                String[] str2s = str2.split(",");
                if (str1s.length > 1 || str2s.length > 1) {
                    if (str1s.length > str2s.length) {
                        for (int i = 0; i < str1s.length; i++) {
                            if (!Arrays.asList(str2s).contains(str1s[i])) {
                                return false;
                            }
                        }
                    } else {
                        for (int i = 0; i < str2s.length; i++) {
                            if (!Arrays.asList(str1s).contains(str2s[i])) {
                                return false;
                            }
                        }
                        return true;
                    }
                }
            }
            if (StringUtils.isBlank(str1)&&StringUtils.isBlank(str2)) {
                return true;
            }
            return str1.equals(str2);
        } else if ("".equals(str2)) {
            return true;
        } else {
            return str1 == str2;
        }
    }

    public static String getEvilDrugType(Integer type){
        if(type!=null) {
            if (type == 1 || type == 2 || type == 3 || type == 4) {
                return "新型毒品";
            } else if (type == 5 || type == 6 || type == 7 || type == 8) {
                return "传统毒品";
            }
        }
        return "";
    }
    public static String getDrugType(Integer type){
        if(type!=null) {
            if (type == 1) {
                return "新型毒品";
            } else if (type == 2) {
                return "传统毒品";
            }
        }
        return "";
    }

    public static String getEvilDrugName(Integer type) {
        if (type != null) {
            switch (type) {
                case 1:
                    return "冰毒";
                case 2:
                    return "麻古";
                case 3:
                    return "摇头丸";
                case 4:
                    return "K粉";
                case 5:
                    return "鸦片";
                case 6:
                    return "海洛因";
                case 7:
                    return "吗啡";
                case 8:
                    return "大麻";
                default:
                    break;
            }
        }
        return "";
    }

    public static String getFile(HttpServletRequest rq, String imgURL, String fileName){
        /**本机测试的时候 */
        /*String realPath = rq.getSession().getServletContext()
                   .getRealPath("files/");*/
        /**部署到服务器的时候 */
        String realPath = imgURL;
        String newName = realPath + fileName + ".xlsx";
        Upload.deleteFile(newName);
        return  realPath;//savepath
    }

    public static String getString(String pram){
        if(pram==null){
            return "";
        }
        return pram;
    }

    /**
     * 比较两个时间之间相差几个月
     * @param startDate 开始时间
     * @param endData 结束时间
     * @return
     */
    public static int monthsBetween(String startDate,String endData){
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM");
        DateTime start = formatter.parseDateTime(startDate);
        DateTime end = formatter.parseDateTime(endData);
        int months = Months.monthsBetween(start, end).getMonths();
        return months;
    }

    /***
     * 获取上一个月份
     * @param date
     * @return
     */
    public static String preMonths(String date){
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM");
        String retDate="";
        try {
            Date currdate = sd.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currdate);
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
            retDate= sd.format(calendar.getTime());
        } catch (ParseException e) {

        }
        return retDate;
    }


    /***
     * 时间间隔内所有的月份
     * by zhgp 2018-9-27
     * @param minDate 开始时间
     * @param maxDate 结束时间
     * @return list
     * EXCEPTION
     */
    public static String[] getMonthBetween(String minDate, String maxDate) throws ParseException {

        List<String> times = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");//格式化为年月
        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();
        if (!StringUtils.isBlank(minDate) || !StringUtils.isBlank(maxDate)) {//不能全为空
            if (StringUtils.isBlank(minDate)) {//if 开始时间为空 取之前6个月
                Calendar c = Calendar.getInstance();
                c.setTime(sdf.parse(maxDate));//set最后时间
                c.add(Calendar.MONTH, -5);//取之前六个月
                min.setTime(c.getTime());
            } else {
                min.setTime(sdf.parse(minDate));
            }
            min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);
            if (StringUtils.isBlank(maxDate)) {//if 结束时间为空 则取当前时间
                max.setTime(new Date());
            } else {
                max.setTime(sdf.parse(maxDate));
            }
            max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);
        } else {//否则取当前时间  到之前六个月
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());//set最后时间
            c.add(Calendar.MONTH, -5);//取之前六个月
            min.setTime(c.getTime());
            min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);
            max.setTime(new Date());
            max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);
        }
        Calendar curr = min;
        while (curr.before(max)) {
            times.add(sdf.format(curr.getTime()));
            curr.add(Calendar.MONTH, 1);
        }
        return times.toArray(new String[times.size()]);
    }

    /**
     * 字符格式和长度验证
     * @param date 要验证的数据()
     * @param msg 提示信息
     * @param minLength 最小长度
     * @param maxLength 最大长度
     * @param regex 验证格式的正则
     * @return JsonResponseData
     */
    public static String validation(String regex,String date,int minLength, int maxLength, String msg){
        DataValidation validation = new DataValidation();
        if(!StringUtils.isBlank(date)){
                if(!StringUtils.isBlank(regex)) {
                    validation.cheFormat(regex, date, msg);
                }
                validation.chkLength(date,minLength,maxLength,msg);
        }
        return null;
    }

    /**
     * @author cat
     * 通过身份证号码获取出生日期、性别、年龄
     * @param certificateNo
     * @return 返回的出生日期格式：1990-01-01   性别格式：F-女，M-男
     */
    public static Map<String, String> getBirAgeSex(String certificateNo) {
        String birthday = "";
        String age = "";
        String sexCode = "";

        int year = Calendar.getInstance().get(Calendar.YEAR);
        char[] number = certificateNo.toCharArray();
        boolean flag = true;
        if (number.length == 15) {
            for (int x = 0; x < number.length; x++) {
                if (!flag) return new HashMap<String, String>();
                flag = Character.isDigit(number[x]);
            }
        } else if (number.length == 18) {
            for (int x = 0; x < number.length - 1; x++) {
                if (!flag) return new HashMap<String, String>();
                flag = Character.isDigit(number[x]);
            }
        }
        if (flag && certificateNo.length() == 15) {
            birthday = "19" + certificateNo.substring(6, 8) + "-"
                    + certificateNo.substring(8, 10) + "-"
                    + certificateNo.substring(10, 12);
            sexCode = Integer.parseInt(certificateNo.substring(certificateNo.length() - 3, certificateNo.length())) % 2 == 0 ? "F" : "M";
            age = (year - Integer.parseInt("19" + certificateNo.substring(6, 8))) + "";
        } else if (flag && certificateNo.length() == 18) {
            birthday = certificateNo.substring(6, 10) + "-"
                    + certificateNo.substring(10, 12) + "-"
                    + certificateNo.substring(12, 14);
            sexCode = Integer.parseInt(certificateNo.substring(certificateNo.length() - 4, certificateNo.length() - 1)) % 2 == 0 ? "F" : "M";
            age = (year - Integer.parseInt(certificateNo.substring(6, 10))) + "";
        }
        Map<String, String> map = new HashMap<String, String>();
        map.put("birthday", birthday);
        map.put("age", age);
        map.put("sexCode", sexCode);
        map.put("sexCodeCH", sexCode=="M"?"男":"女");
        map.put("sexCodeNum", sexCode=="M"?"1":"2");
        return map;
    }

    /**
     * 返回本机IP的字符串形式
     *
     * @return
     * @throws Exception
     */
    public static String getLocalIp(){
        String ip ="";
        try {
            InetAddress inet;

            inet = getSystemLocalIP();
            if (null != inet) {
                ip = inet.getHostAddress();
                return ip;
            }
        } catch (Exception e){
            return null;
        }
        finally {
            return ip;
        }
    }

    /**
     * 获取本机ip的InetAddress形式
     *
     * @return
     * @throws Exception
     */
    private static InetAddress getSystemLocalIP() throws Exception {
        InetAddress inet = null;
        String osName = getSystemOsName();
        if ("Windows".compareToIgnoreCase(osName) < 0) {
            inet = getWinLocalIp();
        } else {
            inet = getUnixLocalIp();
        }
        return inet;
    }

    /**
     * 获取类Unix系统的IP
     *
     * @return
     * @throws Exception
     */
    private static InetAddress getUnixLocalIp() throws Exception {
        Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
        if (null == netInterfaces) {
            throw new Exception("获取类Unix系统的IP失败");
        }
        InetAddress ip = null;
        while (netInterfaces.hasMoreElements()) {
            NetworkInterface ni = netInterfaces.nextElement();
            if (ni.isUp()) {
                Enumeration<InetAddress> addressEnumeration = ni.getInetAddresses();
                while (addressEnumeration.hasMoreElements()) {
                    ip = addressEnumeration.nextElement();
                    if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress()
                            && ip.getHostAddress().indexOf(":") == -1) {
                        return ip;
                    }
                }
            }
        }
        throw new Exception("获取类Unix系统的IP失败");
    }

    /**
     * 获取操作系统名称
     *
     * @return
     */
    private static String getSystemOsName() {
        Properties props = System.getProperties();
        String osName = props.getProperty("os.name");
        return osName;
    }

    /**
     * 获取window系统的ip，貌似不会返回null
     *
     * @return
     * @throws UnknownHostException
     */
    private static InetAddress getWinLocalIp() throws UnknownHostException {
        InetAddress inet = InetAddress.getLocalHost();
        return inet;
    }

    /**
     * 判断字符对象非空
     * @param obj
     * @return
     */
    public static boolean isNullOrEmpty(Object obj) {
        if(obj == null) {
            return true;
        } else if(obj instanceof CharSequence) {
            return ((CharSequence)obj).length() == 0;
        } else if(obj instanceof Collection) {
            return ((Collection)obj).isEmpty();
        } else if(obj instanceof Map) {
            return ((Map)obj).isEmpty();
        } else if(!(obj instanceof Object[])) {
            return false;
        } else {
            Object[] object = (Object[])((Object[])obj);
            if(object.length == 0) {
                return true;
            } else {
                boolean empty = true;

                for(int i = 0; i < object.length; ++i) {
                    if(!isNullOrEmpty(object[i])) {
                        empty = false;
                        break;
                    }
                }

                return empty;
            }
        }
    }

    /**
     * 从路径获取文件名
     * @param url
     * @return
     */
    public static String getFileNameFromUrl(String url) {
        if(StringUtils.isBlank(url)){
            return "";
        }
        String fileName = url.trim().substring(url.lastIndexOf("/")+1);
        return fileName;
    }








//    public static void main(String[] args){
//        deCodeService.getwxacode("123321");
//            System.out.print(bodystr);
//    }


}