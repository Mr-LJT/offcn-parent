package com.offcn.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author LJT
 * @CreateTime: 2021/5/8 20:56
 * @Description:
 */
public class AppDateUtil {

    public static String getFormatTime(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = format.format(new Date());
        return date;
    }

    public static String getFormatTime(String pattern){
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        String date = format.format(new Date());
        return date;
    }

    public static String getFormatTime(String pattern,Date date){
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        String string = format.format(date);
        return string;
    }

}
