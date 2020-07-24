package com.xxty.utils.utils;

import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: llun
 * @DateTime: 2020/7/24 14:57
 * @Description: TODO
 */
public class DateUtils {
    public static void main(String[] args) {
//        Date date = new Date();
//        System.out.println(date);
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
//        System.out.println(simpleDateFormat.format(date));

        String time = "2020-07-20 15:10:00";
//        Date date = strToDateLong(time);
        System.out.println(timeUtile(strToDateLong(time)));


    }
    public static Date strToDateLong(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }
    public static String timeUtile(Date inTime) {
        // 拿到当前时间戳和发布时的时间戳，然后得出时间戳差
        Date curTime = new Date();
        long timeDiff = curTime.getTime() - inTime.getTime();
        //上面一行代码可以换成以下（兼容性的解决）

        // 单位换算
        long min = 60 * 1000;
        long hour = min * 60;
        long day = hour * 24;
        long week = day * 7;
        long month = week * 4;
        long year = month * 12;
        DecimalFormat df = new DecimalFormat("#");
        // 计算发布时间距离当前时间的周、天、时、分
        double exceedyear = Math.floor(timeDiff / year);
        double exceedmonth = Math.floor(timeDiff / month);
        double exceedWeek = Math.floor(timeDiff / week);
        double exceedDay = Math.floor(timeDiff / day);
        double exceedHour = Math.floor(timeDiff / hour);
        double exceedMin = Math.floor(timeDiff / min);


        // 最后判断时间差到底是属于哪个区间，然后return

        if (exceedyear < 100 && exceedyear > 0) {
            return df.format(exceedyear) + "年前";
        } else {
            if (exceedmonth < 12 && exceedmonth > 0) {
                return df.format(exceedmonth) + "月前";
            } else {
                if (exceedWeek < 4 && exceedWeek > 0) {
                    return df.format(exceedWeek) + "星期前";
                } else {
                    if (exceedDay < 7 && exceedDay > 0) {

                        if (exceedDay==1){
                            return "昨天";
                        }else if (exceedDay==2){
                            return "前天";
                        }else {
                            return df.format(exceedDay) + "天前";
                        }

                    } else {
                        if (exceedHour < 24 && exceedHour > 0) {
                            return df.format(exceedHour) + "小时前";
                        } else  {
                            if (exceedMin<5){
                                return  "刚刚";
                            }else {
                                return df.format(exceedMin) + "分钟前";
                            }

                        }
                    }
                }
            }
        }
    }
}
