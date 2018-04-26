package com.zd.common;

import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * Created by zhangxiusheng on 17/3/29.
 * 日期时间帮助类;
 */
public class DateTimeUtils {
    /**
     * 获取当前时间字符串
     * @return
     */
    public String getNowTimeStr() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        return sdf.format(new Date());
    }

    /**
     * 获取两个日期之间的时间差（秒）
     *
     * @param dateTime1
     * @param dateTime2
     * @return
     */
    public long getDifSeconds( String dateTime1, String dateTime2 ) {
        long rlt_mi_cnt = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        try {
            Date date1 = sdf.parse(dateTime1);
            Date date2 = sdf.parse(dateTime2);

            long t1 = date1.getTime();// milliseconds
            long t2 = date2.getTime();// milliseconds

            // 获取时间差:(秒)
            rlt_mi_cnt = (t2 - t1) / 1000;

        } catch (ParseException e) {
            e.printStackTrace();
            rlt_mi_cnt = 0;
        }
        return rlt_mi_cnt;
    }

    /**
     * 指定时间距离当前时间的差额(秒)
     *
     * @param dateTime1
     * @return
     */
    public long getDifSeconds( String dateTime1 ) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String dateTime_now = sdf.format(new Date());
        long rlt_mi_cnt = 0;
        try {
            Date date1 = sdf.parse(dateTime1);
            Date date2 = sdf.parse(dateTime_now);

            long t1 = date1.getTime();// milliseconds
            long t2 = date2.getTime();// milliseconds

            // 获取时间差:(秒)
            rlt_mi_cnt = (t2 - t1) / 1000;

        } catch (ParseException e) {
            e.printStackTrace();
            rlt_mi_cnt = 0;
        }

        return rlt_mi_cnt;
    }
}
