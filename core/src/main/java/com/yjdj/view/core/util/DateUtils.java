package com.yjdj.view.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by wangkai on 16/10/26.
 */
public class DateUtils {
    private static final String DATE_FORMAT = "yyyyMMdd";
    private static final String YM_FORMAT = "yyyyMM";
    private static final String YM2_FORMAT = "yyyyMM";

    /**
     * 字符串转成日期类型
     * @param str
     * @return
     * @throws ParseException
     */
    public static Date getDateByStr(String str) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Date date = sdf.parse(str);
        return date;
    }

    public static Date getDateYearMonthByStr(String str) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(YM_FORMAT);
        Date date = sdf.parse(str);
        return date;
    }

    /**
     * 日期类型转化成字符串
     * @param date
     * @return
     * @throws ParseException
     */
    public static String getStrByDate(Date date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        String str = sdf.format(date);
        return str;
    }
    public static String getStrByDateYM(Date date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(YM_FORMAT);
        String str = sdf.format(date);
        return str;
    }
    /**
     * 获取当年1月1号
     * @param str
     * @return
     * @throws ParseException
     */
    public static String getDateBegin(String str) throws ParseException {
        Date date = getDateByStr(str);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //获取年份
        int year = cal.get(Calendar.YEAR);
        String lastYearStart = year+"0101";
        return lastYearStart;
    }

    /**
     * 获取上年1月1号
     * @param str
     * @return
     * @throws ParseException
     */
    public static String getLastYearStart(String str) throws ParseException {
        Date date = getDateByStr(str);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //获取上年
        cal.add(Calendar.YEAR, -1);
        //获取年份
        int year = cal.get(Calendar.YEAR);
        String lastYearStart = year+"0101";
        return lastYearStart;
    }

    /**
     * 获取上年12月31
     * @param str
     * @return
     * @throws ParseException
     */
    public static String getLastYearEnd(String str) throws ParseException {
        Date date = getDateByStr(str);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //获取上年
        cal.add(Calendar.YEAR, -1);
        //获取年份
        int year = cal.get(Calendar.YEAR);
        String lastYearEnd = year+"1231";
        return lastYearEnd;
    }

    /**
     * 获取上年今天
     * @param str
     * @return
     * @throws ParseException
     */
    public static String getLastDate(String str) throws ParseException {
        Date date = getDateByStr(str);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //获取上年
        cal.add(Calendar.YEAR, -1);
        //获取年份
        Date dateTime = cal.getTime();
        String dateStr = getStrByDate(dateTime);
        return dateStr;
    }

    public static String getYearBeforeStr(String str,int year) throws ParseException {
        Date date = getDateByStr(str);
        return getYearBeforeDate(date,year);
    }

    /**
     * 获取n年前日期
     * @param date
     * @param year
     * @return
     * @throws ParseException
     */
    public static String getYearBeforeDate(Date date,int year) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //获取n年前日期
        cal.add(Calendar.YEAR,-year);
        Date dateTime = cal.getTime();
        String dateStr = getStrByDate(dateTime);
        return dateStr;
    }

    /**
     * 根据日期获取n月前结果
     * @param str
     * @param month
     * @return
     * @throws ParseException
     */
    public static String getMonthBeforeStr(String str,int month) throws ParseException {
        Date date = getDateByStr(str);
        return getMonthBeforeDate(date,month);
    }

    /**
     * 根据日期获取n月前结果
     * @param date
     * @param month
     * @return
     * @throws ParseException
     */
    public static String getMonthBeforeDate(Date date,int month) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //获取n月前日期
        cal.add(Calendar.MONTH,-month);
        Date dateTime = cal.getTime();
        String dateStr = getStrByDate(dateTime);
        return dateStr;
    }

    /**
     * 根据日期获取n日前日期
     * @param str
     * @param day
     * @return
     * @throws ParseException
     */
    public static String getDayBeforeStr(String str,int day) throws ParseException {
        Date date = getDateByStr(str);
        return getDayBeforeDate(date,day);
    }

    /**
     * 根据日期获取n日前日期
     * @param date
     * @param day
     * @return
     * @throws ParseException
     */
    public static String getDayBeforeDate(Date date,int day) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //获取n月前日期
        cal.add(Calendar.DAY_OF_YEAR,-day);
        Date dateTime = cal.getTime();
        String dateStr = getStrByDate(dateTime);
        return dateStr;
    }



    /**
     * 获取上年同月
     * @param str
     * @return
     * @throws ParseException
     */
    public static String getLastYM(String str) throws ParseException {
        Date date = getDateYearMonthByStr(str);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //获取上年
        cal.add(Calendar.YEAR, -1);
        //获取年份
        Date dateTime = cal.getTime();
        String dateStr = getStrByDateYM(dateTime);
        return dateStr;
    }


    /**
     * yyyy0mm
     * @param date yyyymm
     * @return
     */
    public static String formatStringDate(String date){
        String begin = date.substring(0,4);
        String end = date.substring(4);
        return begin+"0"+end;
    }

    /**
     * 根据年月获取单月最后一天
     * @param ym
     * @return
     * @throws ParseException
     */
    public static String getDateStr(String ym) throws ParseException {
        //年月转日期
        SimpleDateFormat sdf = new SimpleDateFormat(YM2_FORMAT);
        Date ymDate = sdf.parse(ym);

        Calendar calendar = Calendar.getInstance();
        // 设置时间,当前时间不用设置
        calendar.setTime(ymDate);
        // 设置日期为本月最大日期
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        Date date = calendar.getTime();

        //日期转年月日
        SimpleDateFormat sdf2 = new SimpleDateFormat(DATE_FORMAT);
        String str = sdf2.format(date);
        return str;
    }


//    public static void main(String[] args){
//        try {
//            System.out.println(DateUtils.getDayBeforeStr("20160120",-6));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//    }


}
